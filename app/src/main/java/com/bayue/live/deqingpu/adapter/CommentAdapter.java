package com.bayue.live.deqingpu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.entity.CommentBean;
import com.bayue.live.deqingpu.entity.ProvinceBean;
import com.bayue.live.deqingpu.utils.Guard;
import com.bayue.live.deqingpu.utils.glide.GlideCircleTransform;
import com.bayue.live.deqingpu.utils.glide.GlideRoundTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.List;


public class CommentAdapter extends BaseAdapter {

	private Context mContext;
	private List<CommentBean.DataBean> mlist = null;
	private RequestManager glideRequest;

	// private Button curDel_btn = null;
	// private UpdateDate mUpdateDate = null;

	public CommentAdapter(Context mContext, List<CommentBean.DataBean> mlist) {
		this.mContext = mContext;
		this.mlist = mlist;
		glideRequest = Glide.with(mContext);
	}

	public int getCount() {

		return mlist.size();
	}

	public Object getItem(int pos) {
		return mlist.get(pos);
	}

	public long getItemId(int pos) {
		return pos;
	}

	public View getView(final int pos, View convertView, ViewGroup p) {

		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.list_item_comment, null);
			viewHolder = new ViewHolder();
			viewHolder.txtCommentUser = (TextView) convertView.findViewById(R.id.txtCommentUser);
			viewHolder.txtCommentContent = (TextView) convertView.findViewById(R.id.txtCommentContent);
			viewHolder.txtCommentStoreReplyTime = (TextView) convertView.findViewById(R.id.txtCommentStoreReplyTime);
			viewHolder.txtCommentStoreReplyContent = (TextView) convertView.findViewById(R.id.txtCommentStoreReplyContent);
			viewHolder.txtCommentUserAddTime = (TextView) convertView.findViewById(R.id.txtCommentUserAddTime);
			viewHolder.linCommentPicture = (LinearLayout) convertView.findViewById(R.id.linCommentPicture);
			viewHolder.imgCommentUserAvator = (ImageView) convertView.findViewById(R.id.imgCommentUserAvator);
			viewHolder.imgCommentPicOne = (ImageView) convertView.findViewById(R.id.imgCommentPicOne);
			viewHolder.imgCommentPicTwo = (ImageView) convertView.findViewById(R.id.imgCommentPicTwo);
			viewHolder.imgCommentPicThree = (ImageView) convertView.findViewById(R.id.imgCommentPicThree);
			viewHolder.rateComment = (RatingBar) convertView.findViewById(R.id.rateComment);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}


		viewHolder.txtCommentUser.setText(mlist.get(pos).getNik_name());
		viewHolder.txtCommentUserAddTime.setText(mlist.get(pos).getAdd_time());
		viewHolder.rateComment.setRating(mlist.get(pos).getComment_rank());
		viewHolder.txtCommentContent.setText(mlist.get(pos).getContent());
		String replyContent = "", replyTime = "";
		List<CommentBean.DataBean.ReplyCommentBean> replyCommentBeanList = mlist.get(pos).getReply_comment();
		if (!Guard.isNull(replyCommentBeanList)){
			if (replyCommentBeanList.size()>0) {
				replyContent = replyCommentBeanList.get(0).getContent();
				replyTime = replyCommentBeanList.get(0).getAdd_time();
			}
		}
		viewHolder.txtCommentStoreReplyContent.setText(replyContent);
		viewHolder.txtCommentStoreReplyTime.setText(replyTime);
		glideRequest.load(mlist.get(pos).getPic())
				.placeholder(R.mipmap.ic_launcher_round)
				.error(R.mipmap.ic_launcher_round)
				.transform(new GlideCircleTransform(mContext))
				.into(viewHolder.imgCommentUserAvator);
		List<String> picList = mlist.get(pos).getComment_img();

		if (!Guard.isNull(picList)) {
			if (picList.size() > 0) {
				viewHolder.linCommentPicture.setVisibility(View.VISIBLE);
				ImageView[] imageViews = new ImageView[]{viewHolder.imgCommentPicOne, viewHolder.imgCommentPicTwo, viewHolder.imgCommentPicThree};
				for (int i = 0; i < picList.size(); i++) {
					glideRequest.load(mlist.get(pos).getComment_img().get(i))
							.placeholder(R.mipmap.ic_launcher_round)
							.error(R.mipmap.ic_launcher_round)
							.transform(new GlideRoundTransform(mContext, 10))
							.into(imageViews[i]);
				}
			} else {
				viewHolder.linCommentPicture.setVisibility(View.GONE);
			}
		}else {
			viewHolder.linCommentPicture.setVisibility(View.GONE);
		}
		return convertView;
	}

	public static class ViewHolder {
		public TextView txtCommentUser,txtCommentContent,txtCommentStoreReplyTime, txtCommentStoreReplyContent, txtCommentUserAddTime;
		public LinearLayout linCommentPicture;
		public ImageView imgCommentUserAvator, imgCommentPicOne, imgCommentPicTwo, imgCommentPicThree;
		public RatingBar rateComment;

	}

}
