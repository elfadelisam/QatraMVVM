package com.example.qatramvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class EducateActivity extends AppCompatActivity {
    SessionManager session;
    TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educate);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.guidelines);

        tv1  = findViewById(R.id.title1);
        tv2  = findViewById(R.id.title2);
        tv3  = findViewById(R.id.title3);
        tv4  = findViewById(R.id.title4);
        tv5  = findViewById(R.id.title5);
        tv6  = findViewById(R.id.title6);
        tv7  = findViewById(R.id.title7);
        tv8  = findViewById(R.id.title8);
        tv9  = findViewById(R.id.title9);
        tv10 = findViewById(R.id.title10);

        tv1.setOnClickListener(view -> {
            String s= tv1.getText().toString().trim();
            Intent intent = new Intent(EducateActivity.this, GuidelinesActivity.class);
            intent.putExtra("guidelines",s+
                    "\n يعتمد نوع فصيلة الدم (Blood Types) على وجود ما يعرف بالأجسام المضادة (Antibodies) ومولدات الضد (Antigens) ويمكن تعريف الأجسام المضادة على أنها بروتينات توجد في البلازما، وتمثل جزءاً مهماً من جهاز المناعة؛ إذ تعمل على تنبيه جهاز المناعة لمهاجمة الجراثيم فور دخولها إلى الجسم، بينما تعتبر مولدات الضد بروتينات توجد على سطح الخلايا الحمراء، وبناءً على ذلك يمكن تقسيم فصائل الدم إلى 8 أنواع هي : "+
                    "\n1-\tفصيلة الدم +A .\n2-\tفصيلة الدم –A .\n3-\tفصيلة الدم +B .\n4-\tفصيلة الدم –B .\n5-\tفصيلة الدم +AB .\n6-\tفصيلة الدم –AB .\n7-\tفصيلة الدم +O .\n8-\tفصيلة الدم –O . "+
                    "\n ومن الجدير بالذكر أنّ خلايا الدم الحمراء قد يوجد على سطحها مولد ضد من نوعٍ آخر يعرف بالعامل الرايزيسي  (Rhesus Factor)واختصاراً RhD، وإذا وجد هذا النوع من مولدات الضد فإنّ فصيلة الدم تحمل إشارة الموجب (Positive)، وإذا لم تحمل خلايا الدم الحمراء مولد الضد هذا فإنّ الفصيلة تحمل إشارة السالب  (Negative).");
            startActivity(intent);
        });

        tv2.setOnClickListener(view ->{
            String s= tv2.getText().toString().trim();
            Intent intent = new Intent(EducateActivity.this,GuidelinesActivity.class);
            intent.putExtra("guidelines",s+"\n الفوائد الإنسانية : \n بتبرعك بالدم، أنت تشارك فى إنقاذ حياة عدة أشخاص فى احتياج مُلِح للدم أو لأحد مشتقاته لأن الدم يمكن فصله لأربعة مكونات رئيسية هى كرات الدم الحمراء والصفائح الدموية والبلازما وبعض عوامل التجلط. بل أن هذه المكونات يمكن فصلها مرة أخرى إلى مكونات أصغر. إن إحساسك بهذا العطاء سوف يشعرك بالرضا والفخر."+
                    "\n الفوائدالصحية : \n • تنشيط خلايا نخاع العظام للمتبرع مما يزيد من فاعليتها ويجدد نشاطها فتنتج المزيد من خلايا الدم الجديدة . \n • التخلص من بعض الحديد الذى يحتويه الدم والذى إذا ما ارتفع مستواه بالدم يزيد من مخاطر الاصابة بامراض القلب ."+
                    "\n • إن الذين يتبرعون بدمهم مرة واحدة على أقل كل سنة هم أقل تعرضا للإصابة بأمراض الدورة الدموية و سرطان الدم . \n • التبرع بالدم ينشط النخاع العظمي، فبينما يتجدد دم الإنسان طبيعيا كل 120 يوما، يتجدد دم المتبرع المنتظم كل 20 يوما فقط، كما أن خلايا الدم الجديدة أنشط فى نقل الأوكسجين إلى أعضاء الجسم، مما يؤدى إلى زيادة النشاط والحيوية.  \n • التحاليل التي تجرى في كل مرة للتبرع تُطمئن المتبرع على صحته بصورة دورية ."+
                    "\n جبال الحسنات : \n • التبرع بالدم هو زكاة عن الصحة وهو فرض كفاية. \n • فضل إنقاذ حياة إنسان ( . . ومن أحياها فكأنما أحيا الناس جميعا . .) . \n • فضل تفريج الكربات فقد قال صلى الله عليه و سلم \" من فرج عن مسلم كربة من كرب الدنيا فرج الله عنه كربة من كرب يوم القيامة\" (رواه الشيخان من حديث ابن عمر، كما في اللؤلؤ والمرجان، برقم 1667).");
            startActivity(intent);
        });

        tv3.setOnClickListener(view -> {
            String s= tv3.getText().toString().trim();
            Intent intent = new Intent(EducateActivity.this,GuidelinesActivity.class);
            intent.putExtra("guidelines",s+ "\n • المصابون في الحوادث بمختلف أنواعها. \n • حالات النزيف قبل الولادة وبعدها. \n • أصحاب العمليات الكبيرة. \n • الأطفال غير مكتملي النمو. \n • حديثي الولادة المصابون بالصفراء. \n • المصابون بأمراض الدم. \n • مرضى الأورام والطب النووي والقي الدموي. \n • مرضى الكبد والغسيل الكلوي والحروق والأمراض المتوطنة.");
            startActivity(intent);
        });

        tv4.setOnClickListener(view -> {
            String s= tv4.getText().toString().trim();
            Intent intent = new Intent(EducateActivity.this,GuidelinesActivity.class);
            intent.putExtra("guidelines",s+ "\n • أن تكون قد تبرعت بالدم خلال الثلاثة أشهر الماضية . \n • وجود أي تعب من المتاعب الصحية التالية :"+
                    "\n 1- جميع أنواع الانيميا (عدا أنيميا نقص الحديد). \n 2- أمراض القلب والحمى الروماتيزمية. \n 3- الامراض الصدرية المزمنة. \n 4- ارتفاع الضغط المزمن. \n 5- الإلتهاب الكبدي الفايروسي. \n 6- مرض السكري. \n 7- حالات تضخم الكبد. \n 8- حالات الفشل الكلوي. \n 9- حالات التشنجات والصرع والاغماء المتكرر. \n 10- زيادة أو نقص افرازات الغدد الدرقية. \n 11- الحمل."+
                    "\n 12- أمراض نزف الدم. \n 13- الأمراض الوراثية. \n 14- الأمراض النفسية. \n 15- أي عمليات خلال فترة ثلاثة أشهر. \n • إذا كان المتبرع يعاني من الاعراض التالية : \n 1- فقدان غير متوقع للوزن والشهية. \n 2- عرق ليلي. \n 3- سخونة ليلية.");
            startActivity(intent);
        });

        tv5.setOnClickListener(view -> {
            String s= tv5.getText().toString().trim();
            Intent intent = new Intent(EducateActivity.this,GuidelinesActivity.class);
            intent.putExtra("guidelines",s+ "\n • لا توجد مضاعفات للتبرع بالدم طالما قام الطبيب بتوقيع الكشف الطبي عليك وأقر ملائمتك بالتبرع . \n • يعوض الجسم كمية الدم التي فقدت خلال ساعات وأغلب الناس يزاولون أنشطتهم العادية بعد التبرع. \n • نادراً ما تحدث بعض الأعراض مثل الدوخة أو القي وتزول تلقائيا بعد فترة وجيزة.");
            startActivity(intent);
        });

        tv6.setOnClickListener(view -> {
            String s= tv6.getText().toString().trim();
            Intent intent = new Intent(EducateActivity.this,GuidelinesActivity.class);
            intent.putExtra("guidelines",s+ "\n وذلك عن طريق جمع الدم في كيس يحتوى على مادة مانعة للتجلط متصل بأبرة معقمة تستعمل لمرة واحدة فقط توصل من الوريد في الذراع وتتم عملية التبرع بالدم في فترة زمنية تقريباً من 5 إلى 10 دقائق، في هذه الفترة يكون المتبرع تحت الرعاية الطبية المباشرة.");
            startActivity(intent);
        });

        tv7.setOnClickListener(view -> {
            String s= tv7.getText().toString().trim();
            Intent intent = new Intent(EducateActivity.this,GuidelinesActivity.class);
            intent.putExtra("guidelines",s+ "\n • الاكثار من شرب السوائل خلال الساعات التالية للتبرع. \n • الامتناع عن التدخين لمدة ساعتين. \n • عدم نزع اللاصق الضاغط على مكان الابرة قبل ساعتين. \n • يجب رفع اليدين إلى أعلى والضغط على مكان الابرة إذا كان هنالك نزيف. \n • عدم القيام بأعمال شاقة أو تمرينات رياضية مجهدة لمدة 24 ساعة. \n • إذا شعر المتبرع بالغثيان والدوران يجب أن يستلقي على السرير ويكون مستوى الرأس منخفضاً عن الجسم أو الجلوس مع وضع الرأس بين الركبتين لمدة 5 دقائق.");
            startActivity(intent);
        });

        tv8.setOnClickListener(view -> {
            String s= tv8.getText().toString().trim();
            Intent intent = new Intent(EducateActivity.this,GuidelinesActivity.class);
            intent.putExtra("guidelines",s+ "\n • ينصح بالتبرع بالدم بعد مرور 6 أشهر من أخر تبرع بالدم في حين أنه لتكرار التبرع يمكن التبرع بالدم قبل ذلك في الفترة من 3 إلى 4 أشهر ولكن يجب أن يكون المتبرع لائق طبياً. ");
            startActivity(intent);
        });

        tv9.setOnClickListener(view -> {
            String s= tv9.getText().toString().trim();
            Intent intent = new Intent(EducateActivity.this,GuidelinesActivity.class);
            intent.putExtra("guidelines",s+ "\n • عن طريق انشاء حساب في التطبيق ومن ثم الدخول على القائمة الرئيسية للتطبيق والضغط على اضافة متبرع ، ومن ثم تعبئة البيانات المطلوبة والنقر على اضافة متبرع. \n • في حالة كانت البيانات التي ادخلتها صحيحة سيتم اضافتها في قاعدة البيانات الخاصة بالمتبرعين. \n • ملاحظة : بمجرد اضافة بياناتك كمتبرع ستصبح قابل للوصول من قبل المستخدمين الذين يبحثون على متبرعين.");
            startActivity(intent);
        });

        tv10.setOnClickListener(view -> {
            String s= tv10.getText().toString().trim();
            Intent intent = new Intent(EducateActivity.this,GuidelinesActivity.class);
            intent.putExtra("guidelines",s+ "\n يمكنك البحث عن المتبرعين عن طريق الدخول للتطبيق ومن ثم الدخول الى التبويب الخاص بالمتبرعين وعرض المتبرعين الذين تتطابق فصائل دمهم معك والتواصل معهم.");
            startActivity(intent);
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}