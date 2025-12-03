package jmj.jpa.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "comments")
@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Comment implements Serializable{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long commentNo;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "reg_date")
	private Date regDate;
	
	@Column(name = "comment_content")
	private String commentContent;
	
}
