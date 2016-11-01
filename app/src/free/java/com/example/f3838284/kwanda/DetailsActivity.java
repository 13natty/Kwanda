package com.example.f3838284.kwanda;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.example.f3838284.kwanda.MainActivity.MyPREFERENCES;

public class DetailsActivity extends AppCompatActivity {

    private SharedPreferences sharedpreferences;
    private int pregnancyDuration;
    private int week;

    @InjectView(R.id.firstcard_text) TextView firstCardTitle;
    @InjectView(R.id.size_text) TextView sizeText;
    @InjectView(R.id.size_text_one) TextView sizeText1;
    @InjectView(R.id.size_text_two) TextView sizeText2;
    @InjectView(R.id.size_text_three) TextView sizeText3;
    @InjectView(R.id.inside_text) TextView insideText;

    @InjectView(R.id.size_image) ImageView sizeImage;
    @InjectView(R.id.MyBg) ImageView myBg;

    @InjectView(R.id.embryo_size) CardView embryoSize;

    @InjectView(R.id.MyToolbar) Toolbar toolbar;

    @InjectView(R.id.collapse_toolbar) CollapsingToolbarLayout collapsingToolbar;

    @InjectView(R.id.MyAppbar) AppBarLayout myAppBar;

    @InjectView(R.id.details_adView) AdView mAdView;
    private int maxWidth;
    private int maxHeight;
    private boolean layedOut = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
		ButterKnife.inject(this);
        
        ToastListener toastListener = new ToastListener(this);

        mAdView.setAdListener(toastListener);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        layedOut = false;
        // Retrieve date records
        String URL = "content://com.example.f3838284.kwanda/data";

        Uri students = Uri.parse(URL);
        Cursor c = managedQuery(students, null, null, null, "FDLP");

        if (c.moveToFirst()) {
            do{
                Toast.makeText(this,
                        c.getString(c.getColumnIndex(MyProvider._ID)) +
                                ", FDLP " +  c.getString(c.getColumnIndex( MyProvider.FDLP)) +
                                ", DeliveryDate " + c.getString(c.getColumnIndex( MyProvider.DeliveryDate)),
                        Toast.LENGTH_SHORT).show();
                pregnancyDuration = Integer.parseInt(c.getString(c.getColumnIndex( MyProvider.PregnancyDuration)));
            } while (c.moveToNext());
        }


        sizeImage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                sizeImage.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if(!layedOut) {
                    //maxHeight = sizeImage.getHeight(); //height is ready
                    maxWidth = sizeImage.getWidth(); //width is ready

                    maxHeight = maxWidth * 160 / 240;

                    ViewGroup.LayoutParams params = sizeImage.getLayoutParams();
                    params.height = maxHeight;
                    sizeImage.requestLayout();
                    layedOut = true;
                }

            }
        });

        sharedpreferences = getSharedPreferences(MyPREFERENCES, getApplicationContext().MODE_PRIVATE);
//        pregnancyDuration = sharedpreferences.getInt(getString(R.string.pref_duration), 1);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        week = pregnancyDuration/7;
        firstCardTitle.setText("Common pregnancy symptoms for week "+week);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        final int screenHeight = displaymetrics.heightPixels;
        final int screenWidth = displaymetrics.widthPixels;

        Bitmap topBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pregnancy_1_weeks_pregnant_female_reproductive_organs);
        int oh = topBitmap.getHeight();
        int ow = topBitmap.getWidth();
        int nw = screenWidth;
        int nh = (oh*nw)/ow;

        myBg.getLayoutParams().height = nh;

        switch (week){
            case 0:
            case 1:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_1_weeks_pregnant_female_reproductive_organs));
                Log.d("TAG", "************** nw "+nw);
                Log.d("TAG", "************** nh "+nh);
                myBg.setBackground(new BitmapDrawable(getResources(),getResizedBitmap(topBitmap, nw, nh)));
                embryoSize.setVisibility(View.GONE);
                sizeText1.setText("48%Breast Pain\n" +
                        "48% of women experience breast pain and tenderness as a symptom during week 1 of pregnancy.");
                sizeText2.setText("44%Fatigue\n" +
                        "44% of women experience fatigue as a symptom during week 1 of pregnancy. ");
                sizeText3.setText("41%Cramps\n" +
                        "41% of women experience cramps as a symptom during week 1 of pregnancy");

                insideText.setText("Ovaries hold eggs at different stages of development. At the end of next week ovulation occurs and a mature egg is released to the fallopian tube.\n" +
                        "\n" +
                        "Fallopian tubes provide a pathway for the newly released egg to travel along on it's way to the uterus.\n" +
                        "\n" +
                        "Your uterus contains a thick lining (endometrium) that accumulated during your last menstrual cycle and is discarded as you have your period.");
                break;
            case 2:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_2_weeks_pregnant_female_reproductive_organs));
                embryoSize.setVisibility(View.GONE);
                sizeText1.setText("53%Breast Pain\n" +
                        "53% of women experience breast pain and tenderness as a symptom during week 2 of pregnancy.");
                sizeText2.setText("43%Fatigue\n" +
                        "43% of women experience fatigue as a symptom during week 2 of pregnancy.");
                sizeText3.setText("41%Bloating\n" +
                        "41% of women experience bloating as a symptom during week 2 of pregnancy.");

                insideText.setText("Your fallopian tube contracts and releases to draw a mature egg into the tube for its journey to the uterus.\n" +
                        "\n" +
                        "The mature egg breaks through the ovary and enters the fallopian tube. Once in the fallopian tube it will meet sperm and become fertilized.\n" +
                        "\n" +
                        "The uterus lining is stimulated and thickens from the hormone progesterone which is produced by the mature egg.");
                break;
            case 3:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_3_weeks_pregnant_female_reproduction_fertilization));
                sizeText1.setText("51%Breast Pain\n" +
                        "51% of women experience breast pain and tenderness as a symptom during week 3 of pregnancy.");
                sizeText2.setText("51%Fatigue\n" +
                        "51% of women experience fatigue as a symptom during week 3 of pregnancy.");
                sizeText3.setText("46%Cramps\n" +
                        "46% of women experience cramping as a symptom during week 3 of pregnancy.");

                sizeImage.setBackgroundResource(R.drawable.week3_embryo_size_poppyseed);
                sizeText.setText("Embryo Size\n" +
                        "Your baby is about the size of a poppy seed during week 3. \n" +
                        "\n" +
                        "LENGTH: 0.03 in / 0.08 cm\n" +
                        "WEIGHT: 0.002 oz / 0.06 g");
                insideText.setText("Fertilization occurs once the sperm swim up the fallopian tube and meets the egg. The sperm carries genetic information and enzymes that help it penetrate into the egg.\n" +
                        "\n" +
                        "The Blastocyst forms as the cells inside the egg rapidly divide. By the end of the week the blastocyst will reach the uterus where the outer lining of cells anchor to the uterine wall. The cell cluster inside the blastocyst creates the beginnings of the embryo.");
                break;
            case 4:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_4_weeks_pregnant_egg_development));
                sizeText1.setText("52%Breast Pain\n" +
                        "52% of women experience breast pain and tenderness as a symptom during week 4 of pregnancy");
                sizeText2.setText("51%Fatigue\n" +
                        "51% of women experience fatigue as a symptom during week 4 of pregnancy. ");
                sizeText3.setText("40%Bloating\n" +
                        "40% of women experience bloating as a symptom during week 4 of pregnancy.");
                sizeImage.setBackgroundResource(R.drawable.week4_embryo_size_sesameseed);
                sizeText.setText("Embryo Size\n" +
                        "Your baby is about the size of a sesame seed during week 4. \n" +
                        "\n" +
                        "LENGTH: 0.05 in / 0.13 cm\n" +
                        "WEIGHT: 0.004 oz / 0.11 g\n");
                insideText.setText("The Uterus Lining produces hormones and creates a clot to prevent blood loss. \n" +
                        "\n" +
                        "The Yolk Sac provides nourishment for the embryo.\n" +
                        "\n" +
                        "The Embryo currently consists of a few layers of cells that will be changing rapidly to form the baby's body.\n" +
                        "\n" +
                        "The Placenta cells will begin to penetrate the lining of the uterus.\n" +
                        "\n" +
                        "The Amniotic Cavity will later become the amniotic sac that protects the baby.");
                break;
            case 5:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_5_weeks_pregnant_embryo_development));
                sizeText1.setText("63%Breast Pain\n" +
                        "63% of women experience breast pain and tenderness as a symptom during week 5 of pregnancy.");
                sizeText2.setText("55%Fatigue\n" +
                        "55% of women experience fatigue as a symptom during week 5 of pregnancy.");
                sizeText3.setText("46%Bloating\n" +
                        "46% of women experience bloating as a symptom during week 5 of pregnancy.");
                sizeImage.setBackgroundResource(R.drawable.week5_embryo_size_sunflowerseed);
                sizeText.setText("Embryo Size\n" +
                        "Your baby is about the size of a sunflower seed during week 5. \n" +
                        "\n" +
                        "LENGTH: 0.09 in / 0.23 cm\n" +
                        "WEIGHT: 0.007 oz / 0.19 g");
                insideText.setText("The Embryo is now forming the beginnings of your baby's body.\n" +
                        "\n" +
                        "The Amniotic Sac surrounds the developing embryo with fluid for protection.\n" +
                        "\n" +
                        "The Umbilical Cord is developing in order to connect the embryo to the placenta.\n" +
                        "\n" +
                        "The Yolk Sac gives nourishment to the embryo temporarily until the placenta is established.");
                break;
            case 6:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_6_weeks_pregnant_embryo_development));
                sizeText1.setText("71%Breast Pain\n" +
                        "71% of women experience breast pain and tenderness as a symptom during week 6 of pregnancy.");
                sizeText2.setText("63%Fatigue\n" +
                        "63% of women experience fatigue as a symptom during week 6 of pregnancy. ");
                sizeText3.setText("44%Nausea\n" +
                        "44% of women experience nausea as a symptom during week 6 of pregnancy.");
                sizeImage.setBackgroundResource(R.drawable.week6_embryo_size_raisin);
                sizeText.setText("Embryo Size\n" +
                        "Your baby is about the size of a raisin during week 6. \n" +
                        "\n" +
                        "LENGTH: 0.15 in / 0.38 cm\n" +
                        "WEIGHT: 0.01 oz / 0.28 g");
                insideText.setText("The Embryo now has 14 somites which will develop into skeletal muscles, and vertebrae.\n" +
                        "\n" +
                        "The eyes start their first signs of developing.\n" +
                        "\n" +
                        "The Arm/Leg Buds are starting to sprout.\n" +
                        "\n" +
                        "The Umbilical Cord is starting to supply some nutrients from the developing placenta.\n" +
                        "\n" +
                        "The Yolk Sac still provides most of the nourishment to the embryo.\n" +
                        "\n" +
                        "Pharyngeal Arches will develop into the head, jaw and neck.");
                break;
            case 7:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_7_weeks_pregnant_embryo_development));
                sizeText1.setText("73%Breast Pain\n" +
                        "73% of women experience breast pain and tenderness as a symptom during week 7 of pregnancy.");
                sizeText2.setText("64%Fatigue\n" +
                        "64% of women experience fatigue as a symptom during week 7 of pregnancy.");
                sizeText3.setText("53%Nausea\n" +
                        "53% of women experience nausea as a symptom during week 7 of pregnancy.");
                sizeImage.setBackgroundResource(R.drawable.week7_embryo_size_blueberry);
                sizeText.setText("Embryo Size\n" +
                        "Your baby is about the size of a blueberry during week 7. \n" +
                        "\n" +
                        "LENGTH: 0.31 in / 0.79 cm\n" +
                        "WEIGHT: 0.02 oz / 0.5 g");
                insideText.setText("The Embryo is at the beginning of vital organ development.\n" +
                        "\n" +
                        "The heart is starting to provide the early stages of circulation.\n" +
                        "\n" +
                        "The Placenta is developing but not yet ready to be the main supply for nutrients to the embryo.\n" +
                        "\n" +
                        "The Arm/Leg Buds are growing rapidly.\n" +
                        "\n" +
                        "The Yolk Sac is getting smaller as it supplies nourishment to the embryo.");

                break;
            case 8:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_8_weeks_pregnant_embryo_fetus_development));
                sizeText1.setText("71%Breast Pain\n" +
                        "71% of women experience breast pain and tenderness as a symptom during week 8 of pregnancy. ");
                sizeText2.setText("71%Fatigue\n" +
                        "71% of women experience fatigue as a symptom during week 8 of pregnancy. ");
                sizeText3.setText("67%Nausea\n" +
                        "67% of women experience nausea as a symptom during week 8 of pregnancy.");
                sizeImage.setBackgroundResource(R.drawable.week8_embryo_size_raspberry);
                sizeText.setText("Embryo Size\n" +
                        "Your baby is about the size of a raspberry during week 8. \n" +
                        "\n" +
                        "LENGTH: 0.58 in / 1.5 cm\n" +
                        "WEIGHT: 0.04 oz / 1 g");
                insideText.setText("The Yolk Sac is rapidly shrinking as the placenta starts to deliver nourishment to the embryo.\n" +
                        "\n" +
                        "The Eyes are growing larger and getting darker.\n" +
                        "\n" +
                        "The Umbilical Cord is starting to provide the early stages of circulation.\n" +
                        "\n" +
                        "The Ears are just starting to form the outer ear.\n" +
                        "\n" +
                        "The Hands & Feet are starting to develop fingers and toes that are still connected.");
                break;
            case 9:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_9_weeks_pregnant_embryo_fetus_development));
                sizeText1.setText("70%Breast Pain\n" +
                        "70% of women experience breast pain and tenderness as a symptom during week 9 of pregnancy.");
                sizeText2.setText("68%Fatigue\n" +
                        "68% of women experience fatigue as a symptom during week 9 of pregnancy. ");
                sizeText3.setText("67%Nausea\n" +
                        "67% of women experience nausea as a symptom during week 9 of pregnancy.");
                sizeImage.setBackgroundResource(R.drawable.week9_embryo_size_cherry);
                sizeText.setText("Embryo Size\n" +
                        "Your baby is about the size of a cherry during week 9. \n" +
                        "\n" +
                        "LENGTH: 0.80 in / 2 cm\n" +
                        "WEIGHT: 0.07 oz / 2 g\n");
                insideText.setText("The Yolk Sac reduces smaller in size and will soon not be needed.\n" +
                        "\n" +
                        "The Placenta continues to mature in preparation to deliver vital nutrients.\n" +
                        "\n" +
                        "The Ears are continuing to develop.\n" +
                        "\n" +
                        "The Eyes are slowly moving into position as the head takes shape.\n" +
                        "\n" +
                        "The Organs are progressing but not ready to perform specific functions.\n" +
                        "\n" +
                        "The Fingers are segmenting into digits but still connected.");
                break;
            case 10:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_10_weeks_pregnant_fetus_development));
                sizeText1.setText("68%Breast Pain\n" +
                        "68% of women experience breast pain and tenderness as a symptom during week 10 of pregnancy.");
                sizeText2.setText("66%Fatigue\n" +
                        "66% of women experience fatigue as a symptom during week 10 of pregnancy.");
                sizeText3.setText("70%Nausea\n" +
                        "70% of women experience nausea as a symptom during week 10 of pregnancy. ");
                sizeImage.setBackgroundResource(R.drawable.week10_embryo_size_strawberry);
                sizeText.setText("Embryo Size\n" +
                        "Your baby is about the size of a strawberry during week 10. \n" +
                        "\n" +
                        "LENGTH: 1.20 in / 3 cm\n" +
                        "WEIGHT: 0.13 oz / 3.7 g");
                insideText.setText("The Yolk Sac will not be needed going forward as the placenta takes over.\n" +
                        "\n" +
                        "The Placenta is now fully developed and will be your baby's lifeline going forward.\n" +
                        "\n" +
                        "The Ears have formed the outer portion but still need to move upward into their final position.\n" +
                        "\n" +
                        "The Umbilical Cord exchanges blood between the placenta and your baby.\n" +
                        "\n" +
                        "The Wrists are now able to bend.\n" +
                        "\n" +
                        "The Fingers & Toes have grown and separated.\n" +
                        "\n" +
                        "The Intestines are developing at the base of the umbilical cord.\n");
                break;
            case 11:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_11_weeks_pregnant_fetus_development));
                sizeText1.setText("66%Breast Pain\n" +
                        "66% of women experience breast pain and tenderness as a symptom during week 11 of pregnancy.");
                sizeText2.setText("73%Fatigue\n" +
                        "73% of women experience fatigue as a symptom during week 11 of pregnancy.");
                sizeText3.setText("72%Nausea\n" +
                        "72% of women experience nausea as a symptom during week 11 of pregnancy.");
                sizeImage.setBackgroundResource(R.drawable.week11_fetus_size_apricot);
                sizeText.setText("Fetus Size\n" +
                        "Your baby is about the size of a apricot during week 11. \n" +
                        "\n" +
                        "LENGTH: 1.7 in / 4.3 cm\n" +
                        "WEIGHT: 0.28 oz / 7.9 g");
                insideText.setText("The Ears have started to move upward into their final position.\n" +
                        "\n" +
                        "The Eyelids have been developing and are currently sealed closed. They will stay like this until about the 26th week.\n" +
                        "\n" +
                        "The Neck is lengthening allowing the fetus to move its head.\n" +
                        "\n" +
                        "The Diaphragm has developed and is able to move.\n" +
                        "\n" +
                        "The Placenta is now circulating food and waste and will continue to grow larger as the pregnancy progresses.\n" +
                        "\n" +
                        "The Ovaries or Testes are starting to develop.");
                break;
            case 12:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_12_weeks_pregnant_fetus_development));
                sizeText1.setText("69%Breast Pain\n" +
                        "69% of women experience breast pain and tenderness as a symptom during week 12 of pregnancy.");
                sizeText2.setText("73%Fatigue\n" +
                        "73% of women experience fatigue as a symptom during week 12 of pregnancy. ");
                sizeText3.setText("67%Nausea\n" +
                        "67% of women experience nausea as a symptom during week 12 of pregnancy. ");
                sizeImage.setBackgroundResource(R.drawable.week12_fetus_size_lime);
                sizeText.setText("Fetus Size\n" +
                        "Your baby is about the size of a lime during week 12. \n" +
                        "\n" +
                        "LENGTH: 2.3 in / 5.8 cm\n" +
                        "WEIGHT: 0.51 oz / 14.4 g");
                insideText.setText("The Ears have moved up into their final position.\n" +
                        "\n" +
                        "The Mouth is moving and your baby can now swallow.\n" +
                        "\n" +
                        "The Heart has progressed and is now beating rapidly at about 165 beats per minute.\n" +
                        "\n" +
                        "The Umbilical Cord has evolved and is now transferring all vital nutrients from your bloodstream.\n" +
                        "\n" +
                        "The Intestines have moved into the abdominal cavity from the base of the umbilical cord.");
                break;
            case 13:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_13_weeks_pregnant_fetus_development));
                sizeText1.setText("61%Breast Pain\n" +
                        "61% of women experience breast pain and tenderness as a symptom during week 13 of pregnancy.");
                sizeText2.setText("68%Fatigue\n" +
                        "68% of women experience fatigue as a symptom during week 13 of pregnancy.");
                sizeText3.setText("63%Nausea\n" +
                        "63% of women experience nausea as a symptom during week 13 of pregnancy.");
                sizeImage.setBackgroundResource(R.drawable.week13_fetus_size_plum);
                sizeText.setText("Fetus Size\n" +
                        "Your baby is about the size of a plum during week 13. \n" +
                        "\n" +
                        "LENGTH: 3.0 in / 7.62 cm\n" +
                        "WEIGHT: 0.9 oz / 25.5 g\n");
                insideText.setText("The Eyes are still sealed and have moved forward to the front of the face.\n" +
                        "\n" +
                        "The Amniotic Sac contains fluid that absorbs movements of your active fetus from you noticing.\n" +
                        "\n" +
                        "The Toes are now fully separate and about the same length.\n" +
                        "\n" +
                        "The Arms & Legs muscles are developing as they lengthen rapidly.");
                break;
            case 14:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_14_weeks_pregnant_fetus_development));
                sizeText1.setText("59%Breast Pain\n" +
                        "59% of women experience breast pain and tenderness as a symptom during week 14 of pregnancy.");
                sizeText2.setText("67%Fatigue\n" +
                        "67% of women experience fatigue as a symptom during week 14 of pregnancy. ");
                sizeText3.setText("44%Headache\n" +
                        "44% of women experience headache as a symptom during week 14 of pregnancy. ");
                sizeImage.setBackgroundResource(R.drawable.week14_fetus_size_lemon);
                sizeText.setText("Fetus Size\n" +
                        "Your baby is about the size of a lemon during week 14. \n" +
                        "\n" +
                        "LENGTH: 3.51 in / 8.92 cm\n" +
                        "WEIGHT: 1.60 oz / 45.4 g\n");
                insideText.setText("The Hair often starts to emerge at this stage on the head and eyebrows.\n" +
                        "\n" +
                        "The Brain and central nervous system components have developed but continue to refine functionality.\n" +
                        "\n" +
                        "The Facial Features are taking shape and becoming more defined.\n" +
                        "\n" +
                        "The Arms have grown long enough to allow your baby to touch their face.");
                break;
            case 15:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_15_weeks_pregnant_fetus_development));
                sizeText1.setText("58%Breast Pain\n" +
                        "58% of women experience breast pain and tenderness as a symptom during week 15 of pregnancy.");
                sizeText2.setText("62%Fatigue\n" +
                        "62% of women experience fatigue as a symptom during week 15 of pregnancy.");
                sizeText3.setText("55%Headache\n" +
                        "55% of women experience headache as a symptom during week 15 of pregnancy. ");
                sizeImage.setBackgroundResource(R.drawable.week15_fetus_size_peach);
                sizeText.setText("Fetus Size\n" +
                        "Your baby is about the size of a peach during week 15. \n" +
                        "\n" +
                        "LENGTH: 4.17 in / 10.59 cm\n" +
                        "WEIGHT: 2.56 oz / 72.6 g");
                insideText.setText("The Inner Ear have formed and your baby is now able to hear you.\n" +
                        "\n" +
                        "The Placenta is undergoing new development to sustain as the life support system for your baby.\n" +
                        "\n" +
                        "The Neck is extending and your baby can rotate its head freely.\n" +
                        "\n" +
                        "The Spinal Cord has fully formed extending the entire length of the vertebral canal.");
                break;
            case 16:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_16_weeks_pregnant_fetus_development));
                sizeText1.setText("50%Breast Pain\n" +
                        "50% of women experience breast pain and tenderness as a symptom during week 16 of pregnancy.");
                sizeText2.setText("53%Fatigue\n" +
                        "53% of women experience fatigue as a symptom during week 16 of pregnancy.");
                sizeText3.setText("53%Headache\n" +
                        "53% of women experience headache as a symptom during week 16 of pregnancy. ");
                sizeImage.setBackgroundResource(R.drawable.week16_fetus_size_avocado);
                sizeText.setText("Fetus Size\n" +
                        "Your baby is about the size of an avocado during week 16. \n" +
                        "\n" +
                        "LENGTH: 4.69 in / 11.9 cm\n" +
                        "WEIGHT: 3.66 oz / 103.8 g");
                insideText.setText("The Face has now developed to the point of making expressions which your baby has no control over.\n" +
                        "\n" +
                        "The Brain is undergoing new development of nerve cells.\n" +
                        "\n" +
                        "The Ears are able to interpret external voices.\n" +
                        "\n" +
                        "The Skin is covered with a protective soft down to regulate its temperature. Fat is starting to form underneath the skin. \n" +
                        "\n" +
                        "The Circulatory System is now working and pumping about 25 quarts a day.");
                break;
            case 17:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_17_weeks_pregnant_fetus_development));
                sizeText1.setText("51%Breast Pain\n" +
                        "51% of women experience breast pain and tenderness as a symptom during week 17 of pregnancy.");
                sizeText2.setText("58%Fatigue\n" +
                        "58% of women experience fatigue as a symptom during week 17 of pregnancy.");
                sizeText3.setText("55%Headache\n" +
                        "55% of women experience headache as a symptom during week 17 of pregnancy. ");
                sizeImage.setBackgroundResource(R.drawable.week17_fetus_size_apple);
                sizeText.setText("Fetus Size\n" +
                        "Your baby is about the size of an apple during week 17. \n" +
                        "\n" +
                        "LENGTH: 5.25 in / 13.3 cm\n" +
                        "WEIGHT: 5.04 oz / 142.3 g\n");
                insideText.setText("Your Baby is very active and has a lot of room to stretch and perform somersaults.\n" +
                        "\n" +
                        "The Eyes are becoming sensitive to light as the retina becomes established.\n" +
                        "\n" +
                        "The Skeleton is changing from rubbery cartilage to hardened bone.\n" +
                        "\n" +
                        "The Heart is beating rapidly and is strong enough for you to pickup with a stethoscope.\n" +
                        "\n" +
                        "The Lungs are getting exercised as your baby moves fluid with breathing movements.");
                break;
            case 18:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_18_weeks_pregnant_fetus_development));
                sizeText1.setText("50%Back Pain\n" +
                        "50% of women experience back pain as a symptom during week 18 of pregnancy.");
                sizeText2.setText("53%Fatigue\n" +
                        "53% of women experience fatigue as a symptom during week 18 of pregnancy.");
                sizeText3.setText("53%Headache\n" +
                        "53% of women experience headache as a symptom during week 18 of pregnancy.");
                sizeImage.setBackgroundResource(R.drawable.week18_fetus_size_orange);
                sizeText.setText("Fetus Size\n" +
                        "Your baby is about the size of an orange during week 18. \n" +
                        "\n" +
                        "LENGTH: 5.67 in / 14.4 cm\n" +
                        "WEIGHT: 6.03 oz / 170.9 g");
                insideText.setText("Blood Vessels are visible through the skin and developing rapidly.\n" +
                        "\n" +
                        "The Ears are now in position. Your baby can hear sounds and may be startled by loud noises.\n" +
                        "\n" +
                        "The Sex Organs are developing internally and externally.\n" +
                        "\n" +
                        "The Arms & Legs have room to adjust and are moving regularly.");
                break;
            case 19:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_19_weeks_pregnant_fetus_development));
                sizeText1.setText("52%Back Pain\n" +
                        "52% of women experience back pain as a symptom during week 19 of pregnancy.");
                sizeText2.setText("55%Fatigue\n" +
                        "55% of women experience fatigue as a symptom during week 19 of pregnancy. ");
                sizeText3.setText("47%Breast Pain\n" +
                        "47% of women experience breast pain and tenderness as a symptom during week 19 of pregnancy. ");
                sizeImage.setBackgroundResource(R.drawable.week19_fetus_size_onion);
                sizeText.setText("Fetus Size\n" +
                        "Your baby is about the size of an onion during week 19. \n" +
                        "\n" +
                        "LENGTH: 6.17 in / 15.7 cm\n" +
                        "WEIGHT: 8.58 oz / 243.2 g\n");
                insideText.setText("Your Uterus is growing and stretching the ligaments that help support your baby. This may cause some aches and discomfort for you.\n" +
                        "\n" +
                        "The Brain is developing specialized regions that process the senses: sight, touch, taste, smell and hearing.\n" +
                        "\n" +
                        "The Arms & Legs are now at the correct proportions to the rest of the body.\n" +
                        "\n" +
                        "The Skin is becoming thicker and more opaque.\n" +
                        "\n" +
                        "The Abdominal Organs, including the liver, intestines, stomach, spleen and gall-bladder are now fully enclosed in the abdominal cavity.");
                break;
            case 20:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_20_weeks_pregnant_fetus_development));
                sizeText1.setText("52%Back Pain\n" +
                        "52% of women experience back pain as a symptom during week 20 of pregnancy.");
                sizeText2.setText("57%Fatigue\n" +
                        "57% of women experience fatigue as a symptom during week 20 of pregnancy.");
                sizeText3.setText("45%Breast Pain\n" +
                        "45% of women experience breast pain and tenderness as a symptom during week 20 of pregnancy.");
                sizeImage.setBackgroundResource(R.drawable.week20_fetus_size_pomegranate);
                sizeText.setText("Fetus Size\n" +
                        "Your baby is about the size of a pomegranate during week 20. \n" +
                        "\n" +
                        "LENGTH: 6.60 in / 26 cm\n" +
                        "WEIGHT: 10.69 oz / 303.1 g");
                insideText.setText("Your Uterus (top) is now at the level of your bellybutton.\n" +
                        "\n" +
                        "The Scalp Hair is developing on your baby's head.\n" +
                        "\n" +
                        "The Eyebrows have filled in and are clearly visible.\n" +
                        "\n" +
                        "The Skin is now coated with a white greasy substance (vernix caseosa) and sprouting fine hair called lanugo hair. This hair normally disappears before birth.\n" +
                        "\n" +
                        "The Genitals are now clearly recognizable.");
                break;

            case 21:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_21_weeks_pregnant_fetus_development));
                sizeText1.setText("54%Back Pain\n" +
                        "54% of women experience back pain as a symptom during week 21 of pregnancy.");
                sizeText2.setText("58%Fatigue\n" +
                        "58% of women experience fatigue as a symptom during week 21 of pregnancy.");
                sizeText3.setText("42%Headache\n" +
                        "42% of women experience headache as a symptom during week 21 of pregnancy. ");
                sizeImage.setBackgroundResource(R.drawable.week21_fetus_size_mango);
                sizeText.setText("Fetus Size\n" +
                        "Your baby is about the size of a mango during week 21. \n" +
                        "\n" +
                        "LENGTH: 10.68 in / 27.1 cm\n" +
                        "WEIGHT: 12.9 oz / 365.7 g\n");
                insideText.setText("The Fingernails & Toenails are now growing but will not harm your baby because of the vernix caseosa, a protective coating on the skin.\n" +
                        "\n" +
                        "The Eyelids are now fully developed but still closed.\n" +
                        "\n" +
                        "Baby Fat has started to cover your baby's body.\n" +
                        "\n" +
                        "The Digestive System is processing amniotic fluid as your baby swallows regularly. A sticky black substance, called Meconium, is accumulating in your baby's bowel and will show up during the first diaper change.");
                break;
            case 22:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_22_weeks_pregnant_fetus_development));
                sizeText1.setText("51%Back Pain\n" +
                        "51% of women experience back pain as a symptom during week 22 of pregnancy.");
                sizeText2.setText("51%Fatigue\n" +
                        "51% of women experience fatigue as a symptom during week 22 of pregnancy. ");
                sizeText3.setText("47%Headache\n" +
                        "47% of women experience headache as a symptom during week 22 of pregnancy. ");
                sizeImage.setBackgroundResource(R.drawable.week22_fetus_size_jicama);
                sizeText.setText("Fetus Size\n" +
                        "Your baby is about the size of jicama during week 22. \n" +
                        "\n" +
                        "LENGTH: 11.03 in / 28 cm\n" +
                        "WEIGHT: 15.32 oz / 434.3 g");
                insideText.setText("Your Uterus is expanding to accommodate your rapidly growing baby. You may notice stretch marks developing on your belly and your belly button may start to pop out from the expansion.\n" +
                        "\n" +
                        "The Eyes are moving under the still fused eyelids. Development of the iris is occuring.\n" +
                        "\n" +
                        "Tooth buds are developing below the gum line.\n" +
                        "\n" +
                        "The Hands are reaching and grabbing the face and umbilical cord.\n" +
                        "\n" +
                        "The Ears are able to pick up sounds. Loud sounds may make your baby respond with a startled movement.\n" +
                        "\n" +
                        "The Bones now contain bone marrow which is creating red and white blood cells.");
                break;
            case 23:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_23_weeks_pregnant_fetus_development));
                sizeText1.setText("56%Back Pain\n" +
                        "56% of women experience back pain as a symptom during week 23 of pregnancy. ");
                sizeText2.setText("55%Fatigue\n" +
                        "55% of women experience fatigue as a symptom during week 23 of pregnancy. ");
                sizeText3.setText("37%Headache\n" +
                        "37% of women experience headache as a symptom during week 23 of pregnancy.");
                sizeImage.setBackgroundResource(R.drawable.week23_fetus_size_grapefruit);
                sizeText.setText("Fetus Size\n" +
                        "Your baby is about the size of a grapefruit during week 23. \n" +
                        "\n" +
                        "LENGTH: 11.49 in / 29.2 cm\n" +
                        "WEIGHT: 1.13 lb / 512.6 g\n");
                insideText.setText("Your Uterus is putting pressure on major veins that return blood from the lower part of your body. This may swelling of your ankles and feet.\n" +
                        "\n" +
                        "Body Fat is being stored which helps with the development of the nervous system.\n" +
                        "\n" +
                        "The Skin pigment is now changing to its final color.\n" +
                        "\n" +
                        "The Feet are now kicking more frequently and with more force.");
                break;
            case 24:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_24_weeks_pregnant_fetus_development));
                sizeText1.setText("37%Headache\n" +
                        "37% of women experience headache as a symptom during week 24 of pregnancy.");
                sizeText2.setText("50%Fatigue\n" +
                        "50% of women experience fatigue as a symptom during week 24 of pregnancy. ");
                sizeText3.setText("36%Acid Reflux\n" +
                        "36% of women experience acid reflux as a symptom during week 24 of pregnancy. ");
                sizeImage.setBackgroundResource(R.drawable.week24_fetus_size_coconut);
                sizeText.setText("Your baby is about the size of a coconut during week 24. \n" +
                        "\n" +
                        "LENGTH: 12.02 in / 30.5 cm\n" +
                        "WEIGHT: 1.39 lb / 630.5 g\n");
                insideText.setText("Your Uterus is expanding day by day and is now an inch or two above your belly button.\n" +
                        "\n" +
                        "Taste Buds are starting to develop at this point.\n" +
                        "\n" +
                        "The Lungs are advancing as the respiratory passageways are forming.\n" +
                        "\n" +
                        "The Heart is now beating consistently and should be easy to detect during a scan.");
                break;
            case 25:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_25_weeks_pregnant_fetus_development));
                sizeText1.setText("60%Back Pain\n" +
                        "60% of women experience back pain as a symptom during week 25 of pregnancy.");
                sizeText2.setText("54%Fatigue\n" +
                        "54% of women experience fatigue as a symptom during week 25 of pregnancy.");
                sizeText3.setText("37%Acid Reflux\n" +
                        "37% of women experience acid reflux as a symptom during week 25 of pregnancy.");
                sizeImage.setBackgroundResource(R.drawable.week25_fetus_size_papaya);
                sizeText.setText("Fetus Size\n" +
                        "Your baby is about the size of a papaya during week 25. \n" +
                        "\n" +
                        "LENGTH: 12.93 in / 32.8 cm\n" +
                        "WEIGHT: 1.49 lb / 675.9 g");
                insideText.setText("The Brain is growing rapidly as the cells are dividing and maturing. Already it can learn and remember.\n" +
                        "\n" +
                        "The Mouth is moving frequently and yawns regularly to regulate blood and fluid in the lungs.\n" +
                        "\n" +
                        "The Ear bones have hardened, so hearing is more acute. Your baby can now recognize its mother’s voice. However, since the ears are more sensitive to deeper sound waves, your baby may hear their father’s voice more easily.\n" +
                        "\n" +
                        "Body Fat is increasing daily, which will make the skin look smoother and less wrinkled.\n");
                break;
            case 26:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_26_weeks_pregnant_fetus_development));
                sizeText1.setText("62%Back Pain\n" +
                        "62% of women experience back pain as a symptom during week 26 of pregnancy.");
                sizeText2.setText("50%Fatigue\n" +
                        "50% of women experience fatigue as a symptom during week 26 of pregnancy.");
                sizeText3.setText("42%Mood Swings\n" +
                        "42% of women experience mood swings as a symptom during week 26 of pregnancy.");
                sizeImage.setBackgroundResource(R.drawable.week26_fetus_size_broccoli);
                sizeText.setText("Fetus Size\n" +
                        "Your baby is about the size of a head of broccoli during week 26. \n" +
                        "\n" +
                        "LENGTH: 13.98 in / 35.5 cm\n" +
                        "WEIGHT: 1.60 lb / 725.7 g");
                insideText.setText("Your Uterus is expanding, pushing your rib cage outward. This can cause rib pain and discomfort. \n" +
                        "\n" +
                        "The Lungs are beginning to produce surfactant, the substance that allows the air sacs in the lungs to inflate and keeps them from collapsing when they deflate.\n" +
                        "\n" +
                        "The Brain continues to evolve with brain wave activity for the visual and auditory systems starting to develop.\n" +
                        "\n" +
                        "The Eyes may begin to open around this time.\n" +
                        "\n" +
                        "The Hands are active and muscle coordination has evolved allowing your baby to put fingers and thumb into their mouth.");
                break;
            case 27:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_27_weeks_pregnant_fetus_development));
                sizeText1.setText("62%Back Pain\n" +
                        "62% of women experience back pain as a symptom during week 27 of pregnancy. ");
                sizeText2.setText("57%Fatigue\n" +
                        "57% of women experience fatigue as a symptom during week 27 of pregnancy.");
                sizeText3.setText("37%Acid Reflux\n" +
                        "37% of women experience acid reflux as a symptom during week 27 of pregnancy.");
                sizeImage.setBackgroundResource(R.drawable.week27_fetus_size_cantaloupe);
                sizeText.setText("Fetus Size\n" +
                        "Your baby is about the size of a cantaloupe during week 27. \n" +
                        "\n" +
                        "LENGTH: 14.68 in / 37.3 cm\n" +
                        "WEIGHT: 1.95 lb / 884.5 g\n");
                insideText.setText("Your Uterus is growing rapidly and may soon start to add pressure to the veins that return blood from your legs. This can cause your leg muscles to cramp on occasion.\n" +
                        "\n" +
                        "The Eyelids are fully functional this week and blink, open and close, regularly.\n" +
                        "\n" +
                        "The Eyebrows & Eyelashes are now visible and hair is growing everyday.\n" +
                        "\n" +
                        "The Legs are getting stronger and are now kicking more often.");
                break;
            case 28:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_28_weeks_pregnant_fetus_development));
                sizeText1.setText("60%Back Pain\n" +
                        "60% of women experience back pain as a symptom during week 28 of pregnancy. ");
                sizeText2.setText("58%Fatigue\n" +
                        "58% of women experience fatigue as a symptom during week 28 of pregnancy. ");
                sizeText3.setText("39%Acid Reflux\n" +
                        "39% of women experience acid reflux as a symptom during week 28 of pregnancy.");
                sizeImage.setBackgroundResource(R.drawable.week28_fetus_size_eggplant);
                sizeText.setText("Fetus Size\n" +
                        "Your baby is about the size of an eggplant during week 28. \n" +
                        "\n" +
                        "LENGTH: 14.99 in / 38.1 cm\n" +
                        "WEIGHT: 2.29 lb / 1038.7 g\n");
                insideText.setText("The Hair is growing longer and thicker.\n" +
                        "\n" +
                        "The Milk Teeth are forming under your babies gums.\n" +
                        "\n" +
                        "The Brain is very active and regularly shows signs of rapid eye movement (REM) during sleep which means your baby is dreaming.\n" +
                        "\n" +
                        "The Lungs are continuing to evolve and have established a cyclical pattern which helps to speed up development.\n");
                break;
            case 29:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_29_weeks_pregnant_fetus_development));
                sizeText1.setText("64%Back Pain\n" +
                        "64% of women experience back pain as a symptom during week 29 of pregnancy. ");
                sizeText2.setText("56%Fatigue\n" +
                        "56% of women experience fatigue as a symptom during week 29 of pregnancy. ");
                sizeText3.setText("40%Acid Reflux\n" +
                        "40% of women experience acid reflux as a symptom during week 29 of pregnancy. ");
                sizeImage.setBackgroundResource(R.drawable.week29_fetus_size_spaghettisquash);
                sizeText.setText("Fetus Size\n" +
                        "Your baby is about the size of a spaghetti squash in week 29. \n" +
                        "\n" +
                        "LENGTH: 15.51 in / 39.4 cm\n" +
                        "WEIGHT: 2.57 lb / 1165.7 g");
                insideText.setText("The Muscles are continuing to grow and mature.\n" +
                        "\n" +
                        "The Umbilical Cord is constantly coiling as your baby performs acrobatics in your uterus. It will not crimp or cut off circulation because of Wharton's jelly, a gelatinous substance.\n" +
                        "\n" +
                        "The Bones are receiving about 225 milligrams of calcium, but are still soft and pliable. Around this time your babies skeleton will begin to slowly harden.\n" +
                        "\n" +
                        "The Head is getting bigger to accommodate the growing brain as it adds billion of neurons.");
                break;
            case 30:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_30_weeks_pregnant_fetus_development));
                sizeText1.setText("59%Back Pain\n" +
                        "59% of women experience back pain as a symptom during week 30 of pregnancy.");
                sizeText2.setText("52%Fatigue\n" +
                        "52% of women experience fatigue as a symptom during week 30 of pregnancy.");
                sizeText3.setText("31%Mood Swings\n" +
                        "31% of women experience mood swings as a symptom during week 30 of pregnancy.");
                sizeImage.setBackgroundResource(R.drawable.week30_fetus_size_cauliflower);
                sizeText.setText("Fetus Size\n" +
                        "Your baby is about the size of cauliflower during week 30. \n" +
                        "\n" +
                        "LENGTH: 15.89 in / 40.4 cm\n" +
                        "WEIGHT: 2.94 lb / 1333.6 g");
                insideText.setText("The Eyes are now able to distinguish between light and dark.\n" +
                        "\n" +
                        "The Brain is developing into separate areas that will eventually control specific functions. \n" +
                        "\n" +
                        "The Body Hair that covers your baby’s body (lanugo) is beginning to disappear.\n" +
                        "\n" +
                        "The Bone Marrow is increasing red blood cell development.\n" +
                        "\n" +
                        "The Amniotic Sac has stopped growing and the amniotic fluid will start to decrease as your baby fills out your uterus.");
                break;
            case 31:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_31_weeks_pregnant_fetus_development));
                sizeText1.setText("71%Back Pain\n" +
                        "71% of women experience back pain as a symptom during week 31 of pregnancy.");
                sizeText2.setText("61%Fatigue\n" +
                        "61% of women experience fatigue as a symptom during week 31 of pregnancy. ");
                sizeText3.setText("45%Acid Reflux\n" +
                        "45% of women experience acid reflux as a symptom during week 31 of pregnancy. ");
                sizeImage.setBackgroundResource(R.drawable.week31_fetus_size_cabbage);
                sizeText.setText("Fetus Size\n" +
                        "Your baby is about the size of cabbage during week 31. \n" +
                        "\n" +
                        "LENGTH: 16.34 in / 41.5 cm\n" +
                        "WEIGHT: 3.35 lb / 1519.5 g");
                insideText.setText("Your Uterus may tighten and contract periodically. It is common to experience these Braxton Hicks contractions in the third trimester.\n" +
                        "\n" +
                        "The Legs still have space to be fully stretched out but periodically cross or curl up over your baby's head.\n" +
                        "\n" +
                        "Fat continues to build and will more than double your baby's weight between now and birth.\n" +
                        "\n" +
                        "The Nervous System has matured to the stage where it can control body temperature rather than relying on the temperature of the amniotic fluid.\n");
                break;
            case 32:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_32_weeks_pregnant_fetus_development));
                sizeText1.setText("72%Back Pain\n" +
                        "72% of women experience back pain as a symptom during week 32 of pregnancy.");
                sizeText2.setText("60%Fatigue\n" +
                        "60% of women experience fatigue as a symptom during week 32 of pregnancy.");
                sizeText3.setText("47%Acid Reflux\n" +
                        "47% of women experience acid reflux as a symptom during week 32 of pregnancy.");
                sizeImage.setBackgroundResource(R.drawable.week32_fetus_size_pineapple);
                sizeText.setText("Fetus Size\n" +
                        "Your baby is about the size of a pineapple during week 32. \n" +
                        "\n" +
                        "LENGTH: 17.04 in / 43.3 cm\n" +
                        "WEIGHT: 3.81 lb / 1728.2 g");
                insideText.setText("Your Uterus is pushing up against your abdomen and diaphragm. This may cause sensations of heart burn and being short of breath.\n" +
                        "\n" +
                        "The Toenails & Fingernails have completely developed to their final form.\n" +
                        "\n" +
                        "The Bones have just about reached their full pre-birth growth.\n" +
                        "\n" +
                        "The Skin is becoming soft and smooth as the layer of soft, downy hair that has covered your baby's skin for the past few months — known as lanugo — starts to fall off.");
                break;
            case 33:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_33_weeks_pregnant_fetus_development));
                sizeText1.setText("74%Back Pain\n" +
                        "74% of women experience back pain as a symptom during week 33 of pregnancy.");
                sizeText2.setText("61%Fatigue\n" +
                        "61% of women experience fatigue as a symptom during week 33 of pregnancy.");
                sizeText3.setText("44%Acid Reflux\n" +
                        "44% of women experience acid reflux as a symptom during week 33 of pregnancy.");
                sizeImage.setBackgroundResource(R.drawable.week33_fetus_size_honeydew);
                sizeText.setText("Fetus Size\n" +
                        "Your baby is about the size of a honeydew during week 33. \n" +
                        "\n" +
                        "LENGTH: 17.65 in / 44.8 cm\n" +
                        "WEIGHT: 4.30 lb / 1950.4 g\n");
                insideText.setText("Fat & Muscle are continuing to build. Your baby will add about a half a pound per week until birth.\n" +
                        "\n" +
                        "The Amniotic Fluid is being digested in the amount of about a pint a day now. The waste is stored in your babies colon and will be excreted after birth.\n" +
                        "\n" +
                        "The Fingernails of your baby are now long enough to reach to the tip of the fingers or beyond and might need trimming once your baby is born. There is a chance your baby may scratch their face before birth.\n" +
                        "\n" +
                        "The Skull bones aren't fused together and quite pliable. This allows them to move and slightly overlap making it easier for your baby to fit through the birth canal.\n");
                break;
            case 34:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_34_weeks_pregnant_fetus_development));
                sizeText1.setText("72%Back Pain\n" +
                        "72% of women experience back pain as a symptom during week 34 of pregnancy.");
                sizeText2.setText("70%Fatigue\n" +
                        "70% of women experience fatigue as a symptom during week 34 of pregnancy.");
                sizeText3.setText("45%Acid Reflux\n" +
                        "45% of women experience acid reflux as a symptom during week 34 of pregnancy.");
                sizeImage.setBackgroundResource(R.drawable.week34_fetus_size_celery);
                sizeText.setText("Fetus Size\n" +
                        "Your baby is about the size of celery during week 34. \n" +
                        "\n" +
                        "LENGTH: 18.12 in / 46 cm\n" +
                        "WEIGHT: 4.82 lb / 2186.3 g");
                insideText.setText("The Amniotic Fluid has reached its maximum level and will decrease in the coming week to make room for your growing baby.\n" +
                        "\n" +
                        "The Digestive System is able to process food as all of the digestive enzymes are now active.\n" +
                        "\n" +
                        "Fat accumulations are plumping up the arms and legs which will help regulate body temperature once born.\n" +
                        "\n" +
                        "The Lungs are almost fully developed at this point and most all babies born prematurely at 34 weeks will survive without complications.");
                break;
            case 35:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_35_weeks_pregnant_fetus_development));
                sizeText1.setText("71%Back Pain\n" +
                        "71% of women experience back pain as a symptom during week 35 of pregnancy.");
                sizeText2.setText("53%Fatigue\n" +
                        "53% of women experience fatigue as a symptom during week 35 of pregnancy.");
                sizeText3.setText("41%Cramps\n" +
                        "41% of women experience cramps as a symptom during week 35 of pregnancy.");
                sizeImage.setBackgroundResource(R.drawable.week35_fetus_size_romaine);
                sizeText.setText("Fetus Size\n" +
                        "Your baby is about the size of romaine lettuce during week 35. \n" +
                        "\n" +
                        "LENGTH: 18.64 in / 47.3 cm\n" +
                        "WEIGHT: 5.33 lb / 2417.6 g");
                insideText.setText("The Mouth is practicing sucking movements so your baby is ready to eat when born.\n" +
                        "\n" +
                        "The Pupils now dilate when the sun or bright light filter into the uterus.\n" +
                        "\n" +
                        "Fat accumulation is the predominant focus of your baby's development at this stage.\n" +
                        "\n" +
                        "The Lungs are currently filled with fluid which will be eliminated when your baby takes its first breath.\n");
                break;
            case 36:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_36_weeks_pregnant_fetus_development));
                sizeText1.setText("67%Back Pain\n" +
                        "67% of women experience back pain as a symptom during week 36 of pregnancy.");
                sizeText2.setText("56%Fatigue\n" +
                        "56% of women experience fatigue as a symptom during week 36 of pregnancy.");
                sizeText3.setText("45%Contractions\n" +
                        "45% of women experience contractions as a symptom during week 36 of pregnancy.");
                sizeImage.setBackgroundResource(R.drawable.week36_fetus_size_napacabbage);
                sizeText.setText("Fetus Size\n" +
                        "Your baby is about the size of Napa cabbage during week 36. \n" +
                        "\n" +
                        "LENGTH: 18.99 in / 48.2 cm\n" +
                        "WEIGHT: 5.84 lb / 2648.9 g\n");
                insideText.setText("The Uterus is crowded as your baby grows making it harder for your baby to move. This being said, you'll probably still feel lots of stretches, rolls and wiggles.\n" +
                        "\n" +
                        "The Amniotic Fluid is decreasing as your baby grows and fills out your uterus.\n" +
                        "\n" +
                        "The Skin is shedding the fine downy hair, lanugo, that has covered you baby’s along with the vernix caseosa. Vernix caseosa is the thick, creamy substance that has protected your baby’s skin while they have been submerged in amniotic fluid.\n" +
                        "\n" +
                        "The Head is most likely pointed down, lower in the pelvis, at this stage which is ideal for natural delivery. This position is called \"lightening\" or \"dropping.\"\n" +
                        "\n" +
                        "The Heart is beating fast at 110 to 160 beats per minute. It will continue at this rate for a couple years after birth, until it slows down to around 70 beats per ");
                break;
            case 37:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_37_weeks_pregnant_fetus_development));
                sizeText1.setText("73%Back Pain\n" +
                        "73% of women experience back pain as a symptom during week 37 of pregnancy.");
                sizeText2.setText("55%Fatigue\n" +
                        "55% of women experience fatigue as a symptom during week 37 of pregnancy. ");
                sizeText3.setText("52%Contractions\n" +
                        "52% of women experience contractions as a symptom during week 37 of pregnancy.");
                sizeImage.setBackgroundResource(R.drawable.week37_fetus_size_butternutsquash);
                sizeText.setText("Fetus Size\n" +
                        "Your baby is about the size of a butter squash during week 37. \n" +
                        "\n" +
                        "LENGTH: 19.51 in / 49.5 cm\n" +
                        "WEIGHT: 6.36 lb / 2884.8 g");
                insideText.setText("The Vellus Hairs are small fine hairs that are growing in place of the lanugo hair on your baby's body. As the lanugo is shed from the skin, it is normal for your developing fetus to consume the hair with the amniotic fluid.\n" +
                        "\n" +
                        "The Hair of most fetuses is full at this point with locks up to an inch or two. This being said, some babies are born without any hair at all!\n" +
                        "\n" +
                        "The Ears are picking up familiar voices and your baby is turning toward them in the womb.\n" +
                        "\n" +
                        "The Umbilical Cord is now passing antibodies to your baby. These antibodies will help protect your baby from disease and germs that they will be exposed to after birth.");
                break;
            case 38:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_38_weeks_pregnant_fetus_development));
                sizeText1.setText("73%Back Pain\n" +
                        "73% of women experience back pain as a symptom during week 38 of pregnancy. ");
                sizeText2.setText("59%Fatigue\n" +
                        "59% of women experience fatigue as a symptom during week 38 of pregnancy.");
                sizeText3.setText("63%Contractions\n" +
                        "63% of women experience contractions as a symptom during week 38 of pregnancy.");
                sizeImage.setBackgroundResource(R.drawable.week38_fetus_size_crenshawmelon);
                sizeText.setText("Fetus Size\n" +
                        "Your baby is about the size of a crenshaw melon during week 38. \n" +
                        "\n" +
                        "LENGTH: 19.98 in / 50.7 cm\n" +
                        "WEIGHT: 7.26 lb / 3293.1 g");
                insideText.setText("The Placenta is now at its full size and is spread out flat against your uterus. It will continue its function of supplying nutrients and antibodies to your baby until he or she is born. These antibodies will help your baby fight off infections during the first few months after birth.\n" +
                        "\n" +
                        "The Bones making up your baby's skeleton have hardened. This process is known as ossification.\n" +
                        "\n" +
                        "The Hands can now grasp very tight and may be curled into fists or holding the umbilical cord.\n" +
                        "\n" +
                        "The Skin has shed most of the downy coat of lanugo that covered your baby for weeks, but you may see some on the upper back and shoulders when he or she arrives.\n");
                break;
            case 39:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_39_weeks_pregnant_fetus_development));
                sizeText1.setText("77%Back Pain\n" +
                        "77% of women experience back pain as a symptom during week 39 of pregnancy.");
                sizeText2.setText("55%Cramps\n" +
                        "55% of women experience cramps as a symptom during week 39 of pregnancy.");
                sizeText3.setText("60%Contractions\n" +
                        "60% of women experience contractions as a symptom during week 39 of pregnancy. ");
                sizeImage.setBackgroundResource(R.drawable.week39_fetus_size_watermelon);
                sizeText.setText("Fetus Size\n" +
                        "Your baby is about the size of a watermelon during week 39. \n" +
                        "\n" +
                        "LENGTH: 20.31 in / 51.6 cm\n" +
                        "WEIGHT: 7.78 lb / 3528.9 g");
                insideText.setText("The Lungs are now moving very rhythmically making 35-45 breathing movements every minute.\n" +
                        "\n" +
                        "Fat is continuing to accumulate at a rate of about 1oz (28 grams) a day. This will help your baby regulate their temperature after birth.\n" +
                        "\n" +
                        "All Organ Systems are developed and ready for life outside the womb.\n" +
                        "\n" +
                        "The Head will soon be settled into a downward position lower in the uterus. As your baby becomes engaged with your pelvis it can take pressure away from your ribs.");
                break;
            case 40:
            default:
                myBg.setBackground(getResources().getDrawable(R.drawable.pregnancy_40_weeks_pregnant_fetus_development));
                sizeText1.setText("79%Back Pain\n" +
                        "79% of women experience back pain as a symptom during week 40 of pregnancy.");
                sizeText2.setText("21%Bloating\n" +
                        "21% of women experience bloating as a symptom during week 40 of pregnancy.");
                sizeText3.setText("72%Contractions\n" +
                        "72% of women experience contractions as a symptom during week 40 of pregnancy.");
                sizeImage.setBackgroundResource(R.drawable.week40_fetus_size_pumpkin);
                sizeText.setText("Fetus Size\n" +
                        "Your baby is about the size of a pumpkin during week 40. \n" +
                        "\n" +
                        "LENGTH: 20.58 in / 52.2 cm\n" +
                        "WEIGHT: 7.98 lb / 3619.7 g");
                insideText.setText("The Eyes are fully formed but are not able to focus after birth. It will take a few weeks for the eyes to fully develop before your baby will be able see you and its surroundings clearly. Caucasian babies are usually born with blue eyes and their true eye color may not reveal itself for weeks or months. African and Asian babies usually have dark grey or brown eyes at birth and change to a true brown or black six months to a year after being born.\n" +
                        "\n" +
                        "The Placenta will separate from the uterus wall after you give birth. You will most likely feel additional contractions and your doctor may help deliver it by pulling on the umbilical cord.\n" +
                        "\n" +
                        "The Amniotic Sac will rupture once you go into labor and the amniotic fluid that has cushioned your baby for many months will flow out of your vagina. This is also known as your water breaking.");
                break;
        }

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

    public Bitmap getResizedBitmap(Bitmap image, int bitmapWidth, int bitmapHeight) {
        return Bitmap.createScaledBitmap(image, bitmapWidth, bitmapHeight, true);
    }
}
