package com.rg.webclient.dto;

public record ApiResponse2(
	int boardArticleIdx,
	int boardIdx,
	String boardName,
	String subject,
	String content,
	String subjectEng,
	String contentEng,
	int hitCount,
	String dateCreated,
	String dateCreatedTime,
	String userIdCreated,
	String userNameCreated,
	String userNameCreatedEng,
	String dateModified,
	String dateModifiedTime,
	String userIdModified,
	String userNameModified,
	String userNameModifiedEng,
	int listLimit,
	int listOffset,
	String openYn,
	String locale,
	String searchKind,
	String searchValue,
	String requestURI
) {

}
