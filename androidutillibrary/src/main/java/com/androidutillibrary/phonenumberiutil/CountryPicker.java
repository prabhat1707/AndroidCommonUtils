package com.androidutillibrary.phonenumberiutil;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidutillibrary.R;
import com.androidutillibrary.phonenumberiutil.CountryList;

import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;


import in.androidUtil.library.phonenumberiutil.CCPCountry;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by pallavi tripathi on 10/12/2018.
 * <p>
 * this class is used to show the Countries
 * list and user can select by clicking on name of country
 */
public class CountryPicker {

    private final int mRequestCode;
    private Context mContext;
    private OnCountryPickerListener mOnCountryPickerListener;
    private List<CCPCountry> mCountries;
    private EditText mSearchEditText;
    private RecyclerView mCountriesRecyclerView;
    private CountriesAdapter adapter;
    private List<CCPCountry> mSearchResults;
    private Dialog mBottomSheetDialog;
    private ImageView mImageClearText;
    private boolean isCountryPickerShowing;
    private TextView mTextViewNoCountryFound;


    public CountryPicker(Context context, Country mCountry, int requestCode) {
        this.mContext = context;
        mRequestCode = requestCode;
        mOnCountryPickerListener = (OnCountryPickerListener) mContext;
        mCountries = CountryList.getLibraryMasterCountriesEnglish();


        sortCountries(mCountries);

        if (!isCountryPickerShowing) {

            showBottomSheet(mContext, mCountry, new DialogInterface() {
                @Override
                public void cancel() {
                    isCountryPickerShowing = false;
                }

                @Override
                public void dismiss() {
                    isCountryPickerShowing = false;
                }
            });
        }
        isCountryPickerShowing = true;
    }


    /**
     * sorting countries acc to name
     *
     * @param mCountries list of countries
     */
    private void sortCountries(@NonNull List<CCPCountry> mCountries) {
        Collections.sort(mCountries, new Comparator<CCPCountry>() {
            @Override
            public int compare(CCPCountry country1, CCPCountry country2) {
                return country1.getCountryName().trim().compareToIgnoreCase(country2.getCountryName().trim());
            }
        });
    }

    /**
     * this method is used to initialise and show bottom sheet dialog
     * with list of countries
     *
     * @param context         context of activity
     * @param selectedCountry country class object to show prior selected country name,flag etc.
     */
    public void showBottomSheet(Context context, Country selectedCountry, final DialogInterface dialogInterface) {
        if (mCountries == null || mCountries.isEmpty()) {
            throw new IllegalArgumentException("No Country Found");
        } else {
            mBottomSheetDialog = new Dialog(context, R.style.FullScreenDialogStyle);
            final View sheetView = View.inflate(context, R.layout.dialog_country_picker, null);
            initiateUi(sheetView, selectedCountry);

            ImageView imgSheetClose = sheetView.findViewById(R.id.image_btm_sht_close_toolbar);
            mSearchEditText = sheetView.findViewById(R.id.edit_country_search);
            mCountriesRecyclerView = sheetView.findViewById(R.id.recycler_countries);
            mTextViewNoCountryFound = sheetView.findViewById(R.id.no_country_found);

            imgSheetClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mBottomSheetDialog.dismiss();
                }
            });
            final AppBarLayout layout = sheetView.findViewById(R.id.app_bar_layout);

            layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
                        sheetView.findViewById(R.id.textView_btm_sht_title).setVisibility(View.VISIBLE);
                        sheetView.findViewById(R.id.view).setVisibility(View.VISIBLE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            layout.setElevation(0);
                        }
                    } else {
                        sheetView.findViewById(R.id.view).setVisibility(View.GONE);
                        sheetView.findViewById(R.id.textView_btm_sht_title).setVisibility(View.GONE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            layout.setElevation(0);
                        }
                    }
                }
            });


            setSearchEditText();
            setupRecyclerView(sheetView);
            mBottomSheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mBottomSheetDialog.setContentView(sheetView);
            mBottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    dialogInterface.cancel();
                }
            });
            mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    dialogInterface.dismiss();
                }
            });
            mBottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface1) {
                    if (dialogInterface != null)
                        dialogInterface.cancel();
                }
            });
            mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface12) {
                    if (dialogInterface != null)
                        dialogInterface.dismiss();
                }
            });
            setStatusBarColorIfPossible(mBottomSheetDialog);
            mImageClearText = sheetView.findViewById(R.id.image_clear_country_text);
            mImageClearText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSearchEditText.setText("");
                    mImageClearText.setVisibility(View.GONE);
                }
            });
            mBottomSheetDialog.show();
        }
    }


    /**
     * setting up recycler view with adapter and setting click listener
     * for recyclerView items,dismissing the dialog after item click
     *
     * @param sheetView view to set data
     */
    @SuppressLint("ClickableViewAccessibility")
    private void setupRecyclerView(View sheetView) {
        mSearchResults = new ArrayList<>();
        mSearchResults.addAll(mCountries);
        adapter = new CountriesAdapter(sheetView.getContext(), mSearchResults,
                new OnCountryPickerListener() {
                    @Override
                    public void onSelectCountry(CCPCountry country, int RequestCode) {
                        if (mOnCountryPickerListener != null) {
                            mOnCountryPickerListener.onSelectCountry(country, mRequestCode);
                            if (mBottomSheetDialog != null) {
                                mBottomSheetDialog.dismiss();

                            }
                            mBottomSheetDialog = null;
                        }
                    }
                }, mRequestCode);
        mCountriesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(sheetView.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mCountriesRecyclerView.setLayoutManager(layoutManager);
        mCountriesRecyclerView.setAdapter(adapter);
//        RecyclerSectionItemDecoration mSectionItemDecoration = new RecyclerSectionItemDecoration(mContext.getResources().getDimensionPixelSize(R.dimen.font22dp),
//                true, // true for sticky, false for not
//                new RecyclerSectionItemDecoration.SectionCallback() {
//                    @Override
//                    public boolean isSection(int position) {
//                        return position == 0 || mSearchResults.get(position).getName()
//                                .charAt(0) != mSearchResults.get(position - 1).getName().charAt(0);
//                    }
//
//                    @Override
//                    public CharSequence getSectionHeader(int position) {
//                        return mSearchResults.get(position).getName().subSequence(0, 1);
//                    }
//                });
//        mCountriesRecyclerView.addItemDecoration(mSectionItemDecoration);
        mCountriesRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(mSearchEditText.getWindowToken(), 0);
                }
                mSearchEditText.clearFocus();
                return false;
            }
        });
    }

    /**
     * adding textChangedListener on fetchAvailableFlight edit text and
     * calling method to fetchAvailableFlight and sort the countries acc to query text
     */
    private void setSearchEditText() {
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Intentionally Empty
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Intentionally Empty
            }

            @Override
            public void afterTextChanged(Editable searchQuery) {
                if (searchQuery.toString().length() > 0) {
                    mImageClearText.setVisibility(View.VISIBLE);
                } else {
                    mImageClearText.setVisibility(View.GONE);
                }
                search(searchQuery.toString());
            }
        });
        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                InputMethodManager imm = (InputMethodManager) mSearchEditText.getContext()
                        .getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(mSearchEditText.getWindowToken(), 0);
                }
                return true;
            }
        });
    }

    /**
     * searching and sorting the countries acc to query
     *
     * @param searchQuery country name to fetchAvailableFlight
     */
    private void search(String searchQuery) {
        mSearchResults.clear();
        for (CCPCountry country : mCountries) {
            if (country.getCountryName().toLowerCase(Locale.ENGLISH).startsWith(searchQuery.toLowerCase())) {
                mSearchResults.add(country);
            }
        }
        if (mSearchResults.isEmpty()) {
            mTextViewNoCountryFound.setVisibility(View.VISIBLE);
            mCountriesRecyclerView.setVisibility(View.GONE);
        } else {
            mTextViewNoCountryFound.setVisibility(View.GONE);
            mCountriesRecyclerView.setVisibility(View.VISIBLE);
            sortCountries(mSearchResults);

        }
        adapter.notifyDataSetChanged();
    }

    /**
     * initialising all views for country list dialog
     *
     * @param sheetView       view to get views
     * @param selectedCountry to show previous selected country
     */
    private void initiateUi(View sheetView, Country selectedCountry) {

        TextView mTxtCountryName = sheetView.findViewById(R.id.text_search_cntry_name);
        TextView mTxtCountryDialCode = sheetView.findViewById(R.id.text_search_cntry_dial_code);
        ImageView mImgFlag = sheetView.findViewById(R.id.image_search_flag);
        if (selectedCountry != null) {
            if (selectedCountry.getName().trim().length() > 15) {
                String countryNameEdited = selectedCountry.getName().trim().substring(0, 14).concat("...");
                mTxtCountryName.setText(countryNameEdited);
            } else {
                mTxtCountryName.setText(selectedCountry.getName());
            }
            mTxtCountryDialCode.setText(selectedCountry.getDial_code());
            mTxtCountryDialCode.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.new_samll_icon, 0);
            mImgFlag.setImageResource(selectedCountry.loadFlagByCode(mContext));

            }

        sortCountries(mCountries);
    }

    private void setStatusBarColorIfPossible(Dialog dialog) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && dialog.getWindow() != null) {
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            dialog.getWindow().setStatusBarColor(ContextCompat.getColor(mContext, R.color.white));
        }
    }


    public interface OnCountryPickerListener {
        void onSelectCountry(CCPCountry country, int RequestCode);
    }


}
