package jmj.jpa.service;

import java.util.List;
import java.util.Map;

import jmj.jpa.model.Comment;
import jmj.jpa.repository.CommentSessionRepository;

public class CommentService {
	CommentSessionRepository commentSessionRepository = 
			new CommentSessionRepository();
	public Comment selectCommentByPrimaryKey(Long commentNo) {
		return commentSessionRepository.selectCommentByPrimaryKey(commentNo);
	}
}
