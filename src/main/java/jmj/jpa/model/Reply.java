package jmj.jpa.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "replies")
@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Reply implements Serializable{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reply_id")
	private Long replyNo;
	
	@Column(name = "comment_id")
	private Long commentNo;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "reply_text")
	private String replyText;
	
	@Column(name = "reg_date")
	private Date regDate;

	public void setReplyContent(String string) {
		// TODO Auto-generated method stub
		
	}
	
}