package com.org.entity;

public class comment {
	public String idcomment;// 评论的id
	public String newsid;// 评论的新闻的id
	public String userid;// 评论者的id
	public String content;// 评论的内容
	public String clike;// 点赞数
	public String time;// 评论的时间
	public String username;// 评论的用户名

	@Override
	public String toString() {
		return "comment [content=" + content + ", idcomment=" + idcomment + ", like=" + clike + ", newsid=" + newsid
				+ ", time=" + time + ", userid=" + userid + ", username=" + username + "]";
	}

}
