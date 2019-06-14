package glit.onetable.model.vo;

import java.sql.Date;

	public class RecipeComment {

		int recipeIdx;
		int userIdx;
		int commentIdx;
		String comment;
		Date commentTime;
		String userName;

		public int getRecipeIdx() {
			return recipeIdx;
		}

		public void setRecipeIdx(int recipeIdx) {
			this.recipeIdx = recipeIdx;
		}

		public int getUserIdx() {
			return userIdx;
		}

		public void setUserIdx(int userIdx) {
			this.userIdx = userIdx;
		}

		public int getCommentIdx() {
			return commentIdx;
		}

		public void setCommentIdx(int commentIdx) {
			this.commentIdx = commentIdx;
		}

		public String getComment() {
			return comment;
		}

		public void setComment(String comment) {
			this.comment = comment;
		}

		public Date getCommentTime() {
			return commentTime;
		}

		public void setCommentTime(Date commentTime) {
			this.commentTime = commentTime;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		@Override
		public String toString() {
			return "replyEx [recipeIdx=" + recipeIdx + ", userIdx=" + userIdx + ", commentIdx=" + commentIdx
					+ ", comment=" + comment + ", commentTime=" + commentTime + ", userName=" + userName + "]";
		}

	}

