package com.rg.toss.controller;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// 코스 결제 공통 코드
public class CommonCode {

	// 카드사 코드
	public static Map<String, String> card = Stream.of(new String[][] {
        { "3K", "기업 BC" },
        { "46", "광주은행" },
        { "71", "롯데카드" },
        { "30", "KDB산업은행" },
        { "31", "BC카드" },
        { "51", "삼성카드" },
        { "38", "새마을금고" },
        { "41", "신한카드" },
        { "62", "신협" },
        { "36", "씨티카드" },
        { "33", "우리BC카드(BC 매입)" },
        { "W1", "우리카드(우리 매입)" },
        { "37", "우체국예금보험" },
        { "39", "저축은행중앙회" },
        { "35", "전북은행" },
        { "42", "제주은행" },
        { "15", "카카오뱅크" },
        { "3A", "케이뱅크" },
        { "24", "토스뱅크" },
        { "21", "하나카드" },
        { "61", "현대카드" },
        { "11", "KB국민카드" },
        { "91", "NH농협카드" },
        { "34", "Sh수협은행" },

        { "6D", "다이너스 클럽" },
        { "4M", "마스터카드" },
        { "3C", "유니온페이" },
        { "7A", "아메리칸 익스프레스" },
        { "4J", "JCB" },
        { "4V", "VISA" },
	}).collect(Collectors.toMap(data -> (String) data[0], data -> data[1]));

	// 은행 코드
	public static Map<String, String> bank = Stream.of(new String[][] {
        { "39", "경남은행" },
        { "34", "광주은행" },
        { "12", "단위농협(지역농축협)" },
        { "32", "부산은행" },
        { "45", "새마을금고" },
        { "64", "산림조합" },
        { "88", "신한은행" },
        { "48", "신협" },
        { "27", "씨티은행" },
        { "20", "우리은행" },
        { "71", "우체국예금보험" },
        { "50", "저축은행중앙회" },
        { "37", "전북은행" },
        { "35", "제주은행" },
        { "90", "카카오뱅크" },
        { "92", "토스뱅크" },
        { "81", "하나은행" },
        { "54", "홍콩상하이은행" },
        { "03", "IBK기업은행" },
        { "06", "KB국민은행" },
        { "31", "DGB대구은행" },
        { "02", "KDB산업은행" },
        { "11", "NH농협은행" },
        { "23", "SC제일은행" },
        { "07", "Sh수협은행" },
	}).collect(Collectors.toMap(data -> (String) data[0], data -> data[1]));

	// 증권사 코드
	public static Map<String, String> stock = Stream.of(new String[][] {
        { "S8", "교보증권" },
        { "SE", "대신증권" },
        { "SK", "메리츠증권" },
        { "S5", "미래에셋증권" },
        { "SM", "부국증권" },
        { "S3", "삼성증권" },
        { "SN", "신영증권" },
        { "S2", "신한금융투자" },
        { "S0", "유안타증권" },
        { "SJ", "유진투자증권" },
        { "SQ", "카카오페이증권" },
        { "SB", "키움증권" },
        { "ST", "토스증권" },
        { "SR", "펀드온라인코리아(한국포스증권)" },
        { "SH", "하나금융투자" },
        { "S9", "하이투자증권" },
        { "S6", "한국투자증권" },
        { "SG", "한화투자증권" },
        { "SA", "현대차증권" },
        { "SI", "DB금융투자" },
        { "S4", "KB증권" },
        { "SP", "KTB투자증권(다올투자증권)" },
        { "SO", "LIG투자증권" },
        { "SL", "NH투자증권" },
        { "SD", "SK증권" },
	}).collect(Collectors.toMap(data -> (String) data[0], data -> data[1]));
	
	// 간편결제사 코드
	public static Map<String, String> easypay = Stream.of(new String[][] {
        { "TOSSPAY", "토스페이" },
        { "NAVERPAY", "네이버페이" },
        { "SAMSUNGPAY", "삼성페이" },
        { "APPLEPAY", "애플페이" },
        { "LPAY", "엘페이" },
        { "KAKAOPAY", "카카오페이" },
        { "PINPAY", "핀페이" },
        { "PAYCO", "페이코" },
        { "SSG", "SSG페이" },
	}).collect(Collectors.toMap(data -> (String) data[0], data -> data[1]));

}
