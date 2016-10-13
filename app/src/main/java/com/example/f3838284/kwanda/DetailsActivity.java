package com.example.f3838284.kwanda;

import android.content.SharedPreferences;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.f3838284.kwanda.MainActivity.MyPREFERENCES;

public class DetailsActivity extends AppCompatActivity {

    private SharedPreferences sharedpreferences;
    private int pregnancyDuration;
    private int week;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView firstCardTitle = (TextView) findViewById(R.id.firstcard_text);
        ImageView sizeImage = (ImageView) findViewById(R.id.size_image);
        TextView sizeText = (TextView) findViewById(R.id.size_text);
        CardView embryoSize = (CardView) findViewById(R.id.embryo_size);
        TextView sizeText1 = (TextView) findViewById(R.id.size_text_one);
        TextView sizeText2 = (TextView) findViewById(R.id.size_text_two);
        TextView sizeText3 = (TextView) findViewById(R.id.size_text_three);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, getApplicationContext().MODE_PRIVATE);
        pregnancyDuration = sharedpreferences.getInt("Duration", 1);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.MyToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);

        final ImageView myBg = (ImageView) findViewById(R.id.MyBg);
        week = pregnancyDuration/7;
        firstCardTitle.setText("Common pregnancy symptoms for week "+week);
        switch (week){
            case 0:
            case 1:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_1_weeks_pregnant_female_reproductive_organs));
                embryoSize.setVisibility(View.GONE);
                sizeText1.setText("48%Breast Pain\n" +
                        "48% of women experience breast pain and tenderness as a symptom during week 1 of pregnancy.");
                sizeText2.setText("44%Fatigue\n" +
                        "44% of women experience fatigue as a symptom during week 1 of pregnancy. ");
                sizeText3.setText("41%Cramps\n" +
                        "41% of women experience cramps as a symptom during week 1 of pregnancy");
                break;
            case 2:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_2_weeks_pregnant_female_reproductive_organs));
                embryoSize.setVisibility(View.GONE);
                sizeText1.setText("53%Breast Pain\n" +
                        "53% of women experience breast pain and tenderness as a symptom during week 2 of pregnancy.");
                sizeText2.setText("43%Fatigue\n" +
                        "43% of women experience fatigue as a symptom during week 2 of pregnancy.");
                sizeText3.setText("41%Bloating\n" +
                        "41% of women experience bloating as a symptom during week 2 of pregnancy.");
                break;
            case 3:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_3_weeks_pregnant_female_reproduction_fertilization));
                sizeText1.setText("51%Breast Pain\n" +
                        "51% of women experience breast pain and tenderness as a symptom during week 3 of pregnancy.");
                sizeText2.setText("51%Fatigue\n" +
                        "51% of women experience fatigue as a symptom during week 3 of pregnancy.");
                sizeText3.setText("46%Cramps\n" +
                        "46% of women experience cramping as a symptom during week 3 of pregnancy.");
                sizeImage.setBackgroundResource(R.mipmap.week3_embryo_size_poppyseed);
                sizeText.setText("Embryo Size\n" +
                        "Your baby is about the size of a poppy seed during week 3. \n" +
                        "\n" +
                        "LENGTH: 0.03 in / 0.08 cm\n" +
                        "WEIGHT: 0.002 oz / 0.06 g");
                break;
            case 4:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_4_weeks_pregnant_egg_development));
                sizeText1.setText("");
                sizeText2.setText("");
                sizeText3.setText("");
                sizeImage.setBackgroundResource(R.mipmap.week4_embryo_size_sesameseed);
                sizeText.setText("Embryo Size\n" +
                        "Your baby is about the size of a sesame seed during week 4. \n" +
                        "\n" +
                        "LENGTH: 0.05 in / 0.13 cm\n" +
                        "WEIGHT: 0.004 oz / 0.11 g\n");
                break;
            case 5:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_5_weeks_pregnant_embryo_development));
                sizeText1.setText("");
                sizeText1.setText("");
                sizeText1.setText("");
                sizeImage.setBackgroundResource(R.mipmap.week5_embryo_size_sunflowerseed);
                sizeText.setText("Embryo Size\n" +
                        "Your baby is about the size of a sunflower seed during week 5. \n" +
                        "\n" +
                        "LENGTH: 0.09 in / 0.23 cm\n" +
                        "WEIGHT: 0.007 oz / 0.19 g");
                break;
            case 6:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_6_weeks_pregnant_embryo_development));
                break;
            case 7:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_7_weeks_pregnant_embryo_development));
                break;
            case 8:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_8_weeks_pregnant_embryo_fetus_development));
                break;
            case 9:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_9_weeks_pregnant_embryo_fetus_development));
                break;
            case 10:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_10_weeks_pregnant_fetus_development));
                break;
            case 11:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_11_weeks_pregnant_fetus_development));
                break;
            case 12:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_12_weeks_pregnant_fetus_development));
                break;
            case 13:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_13_weeks_pregnant_fetus_development));
                break;
            case 14:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_14_weeks_pregnant_fetus_development));
                break;
            case 15:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_15_weeks_pregnant_fetus_development));
                break;
            case 16:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_16_weeks_pregnant_fetus_development));
                break;
            case 17:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_17_weeks_pregnant_fetus_development));
                break;
            case 18:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_18_weeks_pregnant_fetus_development));
                break;
            case 19:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_19_weeks_pregnant_fetus_development));
                break;
            case 20:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_20_weeks_pregnant_fetus_development));
                break;
            case 21:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_21_weeks_pregnant_fetus_development));
                break;
            case 22:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_22_weeks_pregnant_fetus_development));
                break;
            case 23:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_23_weeks_pregnant_fetus_development));
                break;
            case 24:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_24_weeks_pregnant_fetus_development));
                break;
            case 25:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_25_weeks_pregnant_fetus_development));
                break;
            case 26:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_26_weeks_pregnant_fetus_development));
                break;
            case 27:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_27_weeks_pregnant_fetus_development));
                break;
            case 28:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_28_weeks_pregnant_fetus_development));
                break;
            case 29:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_29_weeks_pregnant_fetus_development));
                break;
            case 30:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_30_weeks_pregnant_fetus_development));
                break;
            case 31:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_31_weeks_pregnant_fetus_development));
                break;
            case 32:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_32_weeks_pregnant_fetus_development));
                break;
            case 33:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_33_weeks_pregnant_fetus_development));
                break;
            case 34:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_34_weeks_pregnant_fetus_development));
                break;
            case 35:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_35_weeks_pregnant_fetus_development));
                break;
            case 36:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_36_weeks_pregnant_fetus_development));
                break;
            case 37:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_37_weeks_pregnant_fetus_development));
                break;
            case 38:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_38_weeks_pregnant_fetus_development));
                break;
            case 39:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_39_weeks_pregnant_fetus_development));
                break;
            case 40:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_40_weeks_pregnant_fetus_development));
                break;
            default:
                myBg.setBackground(getResources().getDrawable(R.mipmap.pregnancy_40_weeks_pregnant_fetus_development));
        }

        AppBarLayout myAppBar = (AppBarLayout) findViewById(R.id.MyAppbar);
        myAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener(){
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                float alpha = 1;
                float percentage = ((float)Math.abs(verticalOffset)/appBarLayout.getTotalScrollRange());
                alpha -= percentage;
                Log.d("onOffsetChanged ", "percentage "+alpha);
                if(alpha<1)
                    collapsingToolbar.setTitle("Week "+week);
                else
                    collapsingToolbar.setTitle(" ");
                myBg.setAlpha(alpha);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();

        }
        return true;
    }
}
