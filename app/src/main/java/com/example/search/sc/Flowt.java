package com.example.search.sc;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.search.R;

import java.util.ArrayList;
import java.util.List;

public class Flowt extends ViewGroup {

    private int maxlins;
    private float hoizontal;
    private float verticak;
    private int textmaxlines;
    private int textcolor;
    private int bordercolor;
    private float borderladio;
    private List<String> mdata= new ArrayList<>();
    private itemtextclicklisten itentectlisten=null;

    public Flowt(Context context) {
        this(context,null);
    }

    public Flowt(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    //测量




    public Flowt(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //  <attr name="android:maxLines" format="integer"/>
        //        <attr name="itemhorltslmagin" format="dimension"/>
        //        <attr name="itemverticalmargin" format="dimension"/>
        //        <attr name="textmaxlins" format="integer"/>
        //        <attr name="bordercolor" format="color"/>
        //        <attr name="borderrsdio" format="dimension"/>
        //        <!--        <attr name="textMa"-->
        //        <!--        <attr name=""-->
        //        <attr name="android:lines" format="integer"/>
        //        <attr name="android:textColor" format="color"/>
        //获取属性
        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.Flowt);
        maxlins = a.getInt(R.styleable.Flowt_maxLines,3);
//需要转单位
        hoizontal = a.getDimension(R.styleable.Flowt_itemhorltslmagin,5);
        verticak = a.getDimension(R.styleable.Flowt_itemverticalmargin,5);
        textmaxlines = a.getInt(R.styleable.Flowt_textmaxlins,20);
        textcolor = a.getColor(R.styleable.Flowt_textColor,getResources().getColor(R.color.colorAccent));
        bordercolor = a.getColor(R.styleable.Flowt_bordercolor,getResources().getColor(R.color.colorAccent));
        borderladio = a.getDimension(R.styleable.Flowt_borderrsdio,4);



        a.recycle();


    }
    public void setTextlist(List<String> data){
        this.mdata.clear();
        this.mdata.addAll(data);
        setchidrent();
    }

    private void setchidrent() {
        removeAllViews();
        for (String datum:mdata){

            TextView textView= (TextView) LayoutInflater.from(getContext()).inflate(R.layout.itemflow,this,false);
            textView.setText(datum);
            final  String ittext=datum;
            //设置属性
            //点击事件
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itentectlisten !=null){
                        itentectlisten.itemtextclicklisten(v,ittext);
                    }
                }
            });

            addView(textView);

        }
    }
    //点击

    public  void setitemtextcklilisten(itemtextclicklisten listen){
        this.itentectlisten=listen;
    }
    public interface  itemtextclicklisten{
        void itemtextclicklisten(View view,String Text);
    }
    private List<List<View>>lines=new ArrayList<>();
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode=MeasureSpec.getMode(widthMeasureSpec);
        int sizewide=MeasureSpec.getSize(widthMeasureSpec);
        int sizehig=MeasureSpec.getSize(heightMeasureSpec);


        int chire =getChildCount();
        if (chire==0){

            return;
        }
        lines.clear();

        List<View>line=new ArrayList<>();
        lines.add(line);
    int wide=    MeasureSpec.makeMeasureSpec(sizewide,MeasureSpec.AT_MOST);
        int heig=MeasureSpec.makeMeasureSpec(sizehig,MeasureSpec.AT_MOST);
        for (int i= 0;i<chire;i++){
            View chiree=getChildAt(i);
if (chiree.getVisibility()!=VISIBLE){
    continue;

}
            measureChild(chiree,wide,heig);
            if (line.size()==0){
                line.add(chiree);
            }else {

             boolean canadd=   checketchirelcadd(line,chiree,sizewide);
             if (canadd){
                 line.add(chiree);
             }else {
                 line=new ArrayList<>();
                 lines.add(line);
             }
            }

        }
        View chired=getChildAt(0);
        int childheight=chired.getMeasuredHeight();
        int parentheight=childheight*lines.size()  +(line.size()+1)*5;
        setMeasuredDimension(sizewide,parentheight);
    }

    private boolean checketchirelcadd(List<View> line, View chiree, int sizewide) {
        int measurewith=chiree.getMeasuredWidth();
        int total =50;
        for (View view:line){
            total +=view.getMeasuredWidth() + 50;
        }
        total +=measurewith;

        return total<=sizewide;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//实现布局
        View firstch=getChildAt(0);
        int left=0;
        int right=0;
        int top=50;
        int buttom=firstch.getMeasuredHeight()+50;
        for (List<View>line:lines){
            for (View view:line){
                int hei=view.getMeasuredHeight();
                int wi=view.getMeasuredWidth();
                right +=wi;
//                buttom +=hei;
                view.layout(left,top,right,buttom);
                left=right +50;
                right +=50;
            }
            left=0;
            right=0;
            buttom +=firstch.getMeasuredHeight()+50;

            top  +=firstch.getMeasuredHeight()+50;
        }
    }
}
