import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.os.Build
import android.util.Log
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

object ImageResizeUtils {
    /**
     * 이미지의 너비를 변경한다.
     * @param file
     * @param newFile
     * @param newWidth
     * @param isCamera
     */
    fun resizeFile(file: File, newFile: File?, newWidth: Int, isCamera: Boolean) {
        val TAG = "ImageResizeUtils"
        var originalBm: Bitmap? = null
        var resizedBitmap: Bitmap? = null
        try {
            val options = BitmapFactory.Options()
            options.inPurgeable = true
            options.inDither = true
            originalBm = BitmapFactory.decodeFile(file.absolutePath, options)
            if (isCamera) { // 카메라인 경우 이미지를 상황에 맞게 회전시킨다
                try {
                    val exif = ExifInterface(file.absolutePath)
                    val exifOrientation = exif.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL
                    )
                    val exifDegree = exifOrientationToDegrees(exifOrientation)
                    Log.d(TAG, "exifDegree : $exifDegree")
                    originalBm = rotate(originalBm, exifDegree)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (originalBm == null) {
                Log.e(TAG, "파일 에러")
                return
            }
            val width = originalBm.width
            val height = originalBm.height
            val aspect: Float
            val scaleWidth: Float
            val scaleHeight: Float
            if (width > height) {
                if (width <= newWidth) return
                aspect = width.toFloat() / height
                scaleWidth = newWidth.toFloat()
                scaleHeight = scaleWidth / aspect
            } else {
                if (height <= newWidth) return
                aspect = height.toFloat() / width
                scaleHeight = newWidth.toFloat()
                scaleWidth = scaleHeight / aspect
            }
            // create a matrix for the manipulation
            val matrix = Matrix()
            // resize the bitmap
            matrix.postScale(scaleWidth / width, scaleHeight / height)
            // recreate the new Bitmap
            resizedBitmap = Bitmap.createBitmap(originalBm, 0, 0, width, height, matrix, true)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                resizedBitmap.compress(CompressFormat.JPEG, 80, FileOutputStream(newFile))
            } else {
                resizedBitmap.compress(CompressFormat.PNG, 80, FileOutputStream(newFile))
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } finally {
            originalBm?.recycle()
            resizedBitmap?.recycle()
        }
    }

    /**
     * EXIF 정보를 회전각도로 변환하는 메서드
     *
     * @param exifOrientation EXIF 회전각
     * @return 실제 각도
     */
    fun exifOrientationToDegrees(exifOrientation: Int): Int {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270
        }
        return 0
    }

    /**
     * 이미지를 회전시킵니다.
     *
     * @param bitmap 비트맵 이미지
     * @param degrees 회전 각도
     * @return 회전된 이미지
     */
    fun rotate(bitmap: Bitmap?, degrees: Int): Bitmap? {
        var bitmap = bitmap
        if (degrees != 0 && bitmap != null) {
            val m = Matrix()
            m.setRotate(
                degrees.toFloat(), bitmap.width.toFloat() / 2,
                bitmap.height.toFloat() / 2
            )
            try {
                val converted = Bitmap.createBitmap(
                    bitmap, 0, 0,
                    bitmap.width, bitmap.height, m, true
                )
                if (bitmap != converted) {
                    bitmap.recycle()
                    bitmap = converted
                }
            } catch (ex: OutOfMemoryError) { // 메모리가 부족하여 회전을 시키지 못할 경우 그냥 원본을 반환합니다.
            }
        }
        return bitmap
    }
}