package com.example.saveme.model

import com.google.gson.annotations.SerializedName

class GetShelterList {
    @SerializedName("noticeEdt")    // 공고종료일
    val noticeEdt: String = ""
    @SerializedName("popfile")  // Image
    val popfile: String = ""
    @SerializedName("processState") // 상태
    val processState: String = ""
    @SerializedName("sexCd")    // 성별
    val sexCd: String = ""
    @SerializedName("neuterYn") // 중성화여부
    val neuterYn: String = ""
    @SerializedName("specialMark")  // 특징
    val specialMark: String = ""
    @SerializedName("careNm")   // 보호소이름
    val careNm: String = ""
    @SerializedName("careTel")  // 보소호 전화번호
    val careTel = ""
    @SerializedName("careAddr") // 보호장소
    val careAddr: String = ""
    @SerializedName("orgNm")    // 관할기관
    val orgNm: String = ""
    @SerializedName("chargeNm") // 담당자
    val chargeNm: String = ""
    @SerializedName("officetel")    // 담당자 연락처
    val officetel: String = ""
    @SerializedName("noticeComment")    // 특이사항
    val noticeComment: String = ""
    @SerializedName("numOfRows")    // 한페이지 결과수
    val numOfRows: String = ""
    @SerializedName("pageNo")   // 페이지 번호
    val pageNo: String = ""
    @SerializedName("totalCount")   // 전체 결과 수
    val totalCount: String = ""
    @SerializedName("resultCode")   // 결과 코드
    val resultCode: String = ""
    @SerializedName("resultMsg") // 결과 메세지
    val resultMsg: String = ""
    @SerializedName("desertionNo")    // 유기번호
    val desertionNo: String = ""
    @SerializedName("filename")    // 썸네일 이미지
    val filename: String = ""
    @SerializedName("happenDt")    // 접수일
    val happenDt: String = ""
    @SerializedName("happenPlace")   // 발견장소
    val happenPlace: String = ""
    @SerializedName("kindCd")   // 품종
    val kindCd: String = ""
    @SerializedName("colorCd")   // 색상
    val colorCd: String = ""
    @SerializedName("age")    // 색상
    val age: String = ""
    @SerializedName("weight")   // 체중
    val weight: String = ""
    @SerializedName("noticeNo")   // 공고번호
    val noticeNo: String = ""
    @SerializedName("noticeSdt")   // 공고시작일
    val noticeSdt: String = ""
}