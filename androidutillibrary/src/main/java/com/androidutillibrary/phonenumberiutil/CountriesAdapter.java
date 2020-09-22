package com.androidutillibrary.phonenumberiutil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidutillibrary.R;

import java.util.List;

import in.androidUtil.library.phonenumberiutil.ParsedNumberData;


/**
 * Created by pallavi Tripathi on 06/08/2020.
 * this adapter will be used to show the countries list when clicked on
 */

public class CountriesAdapter extends
        RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder> {

    private final int mRequstCode;
    private CountryPicker.OnCountryPickerListener mListener;
    private List<ParsedNumberData> mCountries;
    private Context mContext;

    /**
     * constructor to initialise adapter
     *
     * @param mContext   activity context
     * @param mCountries list of countries
     * @param mListener  item click listener instance
     */
    public CountriesAdapter(Context mContext, List<ParsedNumberData> mCountries,
                            CountryPicker.OnCountryPickerListener mListener, int requestCode) {
        this.mContext = mContext;
        this.mCountries = mCountries;
        this.mListener = mListener;
        this.mRequstCode = requestCode;
    }

    @NonNull
    @Override
    public CountriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false);
        return new CountriesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesViewHolder holder, int position) {
        final ParsedNumberData country = mCountries.get(position);
        holder.mCountryNameText.setText(country.getCountryName());
        holder.mTxtCntryDialCode.setText(country.getCoutryCode());
        holder.mTxtCntryDialCode.setText(mContext.getString(R.string.dial_code, country.getCoutryCode()));
        holder.mCountryFlagImageView.setImageResource(country.loadFlagByCode(mContext));
        holder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSelectCountry(country);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mCountries.size();
    }

    class CountriesViewHolder extends RecyclerView.ViewHolder {
        private ImageView mCountryFlagImageView;
        private TextView mCountryNameText;
        private TextView mTxtCntryDialCode;
        private RelativeLayout mRootView;

        CountriesViewHolder(View itemView) {
            super(itemView);
            mCountryFlagImageView = itemView.findViewById(R.id.image_country_flag);
            mCountryNameText = itemView.findViewById(R.id.text_country_title);
            mRootView = itemView.findViewById(R.id.rootView);
            mTxtCntryDialCode = itemView.findViewById(R.id.text_country_code);
        }
    }
}
