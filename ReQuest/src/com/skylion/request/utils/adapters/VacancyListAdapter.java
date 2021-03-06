package com.skylion.request.utils.adapters;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.skylion.request.R;
import com.skylion.request.entity.Vacancy;
import com.skylion.request.utils.ExpandableViewHelper;

public class VacancyListAdapter extends BaseAdapter implements OnClickListener {

	private View view;

	private List<Vacancy> requestList;

	private LayoutInflater inflater;

	public VacancyListAdapter(Context context, List<Vacancy> requestList) {
		this.requestList = requestList;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return requestList.size();
	}

	@Override
	public Object getItem(int position) {
		return requestList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Vacancy vacancy = requestList.get(position);
		view = convertView;
		if (view == null)
			view = inflater.inflate(R.layout.vacancy_list_item, null);

		TextView title = (TextView) view.findViewById(R.id.vacancyItem_titleText);
		TextView fund = (TextView) view.findViewById(R.id.vacancyItem_prizeText);
		TextView company = (TextView) view.findViewById(R.id.vacancyItem_companyText);
		TextView created = (TextView) view.findViewById(R.id.vacancyItem_createdText);
		TextView description= (TextView) view.findViewById(R.id.vacancyItem_desciptionText);
		ImageView image = (ImageView) view.findViewById(R.id.vacancyItem_imageView);
		// TextView criteriaSize = (TextView)
		// view.findViewById(R.id.CaseListItem_caseCriteriaSize);
		// TextView expireDate = (TextView)
		// view.findViewById(R.id.CaseListItem_caseExpDate);
		// final TextView authorText = (TextView) view.findViewById(R.id.vacancyItem_authorText);
		// TextView rateText = (TextView)
		// view.findViewById(R.id.CaseListItem_caseRate);
		// LinearLayout mainLayout = (LinearLayout)
		// view.findViewById(R.id.CaseListItem_mainLayout);

		ParseFile applicantResume = (ParseFile) vacancy.getImage();
		if(applicantResume != null)
			loadImage(applicantResume, image);
		
		title.setText(vacancy.getTitle());
		company.setText(vacancy.getCompany());
		
		created.setText(vacancy.getCreatedAtToString());

		description.setText(vacancy.getDescription());

		LinearLayout contentLayout = (LinearLayout) view.findViewById(R.id.vacancyItem_contentLayout);
		contentLayout.setOnClickListener(this);
		
		LinearLayout expandableLayout = (LinearLayout) view.findViewById(R.id.vacancyItem_expandableLayout);
		expandableLayout.setVisibility(View.GONE);
		
		Button buyButton = (Button) view.findViewById(R.id.vacancyItem_buyButton);
		buyButton.setOnClickListener(this);
		
		Button recommendButton = (Button) view.findViewById(R.id.vacancyItem_recommendButton);
		recommendButton.setOnClickListener(this);
		// ParseRelation<ParseObject> relation = request.getRelation("user");
		// ParseQuery<ParseObject> query = relation.getQuery();
		// query.findInBackground(new FindCallback<ParseObject>() {
		//
		// @Override
		// public void done(List<ParseObject> authors, ParseException arg1) {
		// authorText.setText(authors.get(0).toString());
		// }
		// });

		// now execute the query

		// expireDate.setText("[" +
		// DateFormatter.getFormatDate(caseBean.getExpireDate()) + "]");
		fund.setText("$" + vacancy.getReward());

		// criteriaSize.setText("[" + context.getString(R.string.criteria_text)
		// + " " + caseBean.getCriteriaNumber() + "]");
		return view;
	}

	private void loadImage(ParseFile imgFile, final ImageView imgView) {
		
		imgFile.getDataInBackground(new GetDataCallback() {
			public void done(byte[] data, ParseException e) {
				if (e == null) {
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inSampleSize = 1;
					Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
					imgView.setImageBitmap(bitmap);
				} else {
					imgView.setVisibility(View.GONE);
				}
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.vacancyItem_buyButton:
			Toast.makeText(v.getContext(), "Buy answers!", Toast.LENGTH_SHORT).show();
			break;
		case R.id.vacancyItem_recommendButton:
			Toast.makeText(v.getContext(), "Recommend friend!", Toast.LENGTH_SHORT).show();
			break;
		case R.id.vacancyItem_contentLayout:
			LinearLayout expandableLayout = (LinearLayout) v.findViewById(R.id.vacancyItem_expandableLayout);
			if (expandableLayout.isShown()) {
				expandableLayout.setVisibility(View.GONE);
				ExpandableViewHelper.slideIntoDirection(v.getContext(), expandableLayout,R.anim.item_slide_up);
				
			} else {
				expandableLayout.setVisibility(View.VISIBLE);
				ExpandableViewHelper.slideIntoDirection(v.getContext(), expandableLayout,R.anim.item_slide_down);
			}
			break;
		default:
			break;
		}
	}
}
