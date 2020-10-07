package com.onekus.onekus;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_4 extends Fragment {


    static Boolean isValidCode;
    String nowGrade;

    EditText etCode;
    ImageButton btnAdd;
    ImageButton btnRemove;

    Button      btn0;
    Button btn1;
    Button      btn2;
    Button      btn3;
    Button      btn4;

    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    RecyclerAdapter     recyclerAdapter;
    ProgressDialog progressDialog;

    public Fragment_4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_4, container, false);

        isValidCode = false;
        nowGrade = "전체";

        etCode = view.findViewById(R.id.etCode);


        btnAdd = view.findViewById(R.id.btnAdd);
        btnRemove = view.findViewById(R.id.btnRemove);

        btn0 = view.findViewById(R.id.btn0);
        btn1 = view.findViewById(R.id.btn1);
        btn2 = view.findViewById(R.id.btn2);
        btn3 = view.findViewById(R.id.btn3);
        btn4 = view.findViewById(R.id.btn4);

        btn0.setBackground(getResources().getDrawable(R.drawable.primary_round));
        btn0.setTextColor(Color.parseColor("#ffffff"));

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowGrade = "전체";

                btn0.setBackground(getResources().getDrawable(R.drawable.primary_round));
                btn0.setTextColor(getResources().getColor(R.color.colorWhite));

                btn1.setBackground(getResources().getDrawable(R.drawable.gray_round));
                btn1.setTextColor(getResources().getColor(R.color.colorDarkGray));
                btn2.setBackground(getResources().getDrawable(R.drawable.gray_round));
                btn2.setTextColor(getResources().getColor(R.color.colorDarkGray));
                btn3.setBackground(getResources().getDrawable(R.drawable.gray_round));
                btn3.setTextColor(getResources().getColor(R.color.colorDarkGray));
                btn4.setBackground(getResources().getDrawable(R.drawable.gray_round));
                btn4.setTextColor(getResources().getColor(R.color.colorDarkGray));

                if(MainActivity.favorites.size() == 0) {
                    Toast.makeText(getContext(), "관심강의가 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    parse();
                }
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowGrade = "1학년";

                btn1.setBackground(getResources().getDrawable(R.drawable.primary_round));
                btn1.setTextColor(getResources().getColor(R.color.colorWhite));

                btn0.setBackground(getResources().getDrawable(R.drawable.gray_round));
                btn0.setTextColor(getResources().getColor(R.color.colorDarkGray));
                btn2.setBackground(getResources().getDrawable(R.drawable.gray_round));
                btn2.setTextColor(getResources().getColor(R.color.colorDarkGray));
                btn3.setBackground(getResources().getDrawable(R.drawable.gray_round));
                btn3.setTextColor(getResources().getColor(R.color.colorDarkGray));
                btn4.setBackground(getResources().getDrawable(R.drawable.gray_round));
                btn4.setTextColor(getResources().getColor(R.color.colorDarkGray));

                if(MainActivity.favorites.size() == 0) {
                    Toast.makeText(getContext(), "관심강의가 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    parse();
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowGrade = "2학년";

                btn2.setBackground(getResources().getDrawable(R.drawable.primary_round));
                btn2.setTextColor(getResources().getColor(R.color.colorWhite));

                btn0.setBackground(getResources().getDrawable(R.drawable.gray_round));
                btn0.setTextColor(getResources().getColor(R.color.colorDarkGray));
                btn1.setBackground(getResources().getDrawable(R.drawable.gray_round));
                btn1.setTextColor(getResources().getColor(R.color.colorDarkGray));
                btn3.setBackground(getResources().getDrawable(R.drawable.gray_round));
                btn3.setTextColor(getResources().getColor(R.color.colorDarkGray));
                btn4.setBackground(getResources().getDrawable(R.drawable.gray_round));
                btn4.setTextColor(getResources().getColor(R.color.colorDarkGray));

                if(MainActivity.favorites.size() == 0) {
                    Toast.makeText(getContext(), "관심강의가 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    parse();
                }
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowGrade = "3학년";

                btn3.setBackground(getResources().getDrawable(R.drawable.primary_round));
                btn3.setTextColor(getResources().getColor(R.color.colorWhite));

                btn0.setBackground(getResources().getDrawable(R.drawable.gray_round));
                btn0.setTextColor(getResources().getColor(R.color.colorDarkGray));
                btn2.setBackground(getResources().getDrawable(R.drawable.gray_round));
                btn2.setTextColor(getResources().getColor(R.color.colorDarkGray));
                btn1.setBackground(getResources().getDrawable(R.drawable.gray_round));
                btn1.setTextColor(getResources().getColor(R.color.colorDarkGray));
                btn4.setBackground(getResources().getDrawable(R.drawable.gray_round));
                btn4.setTextColor(getResources().getColor(R.color.colorDarkGray));

                if(MainActivity.favorites.size() == 0) {
                    Toast.makeText(getContext(), "관심강의가 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    parse();
                }
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowGrade = "4학년";

                btn4.setBackground(getResources().getDrawable(R.drawable.primary_round));
                btn4.setTextColor(getResources().getColor(R.color.colorWhite));

                btn0.setBackground(getResources().getDrawable(R.drawable.gray_round));
                btn0.setTextColor(getResources().getColor(R.color.colorDarkGray));
                btn2.setBackground(getResources().getDrawable(R.drawable.gray_round));
                btn2.setTextColor(getResources().getColor(R.color.colorDarkGray));
                btn3.setBackground(getResources().getDrawable(R.drawable.gray_round));
                btn3.setTextColor(getResources().getColor(R.color.colorDarkGray));
                btn1.setBackground(getResources().getDrawable(R.drawable.gray_round));
                btn1.setTextColor(getResources().getColor(R.color.colorDarkGray));

                if(MainActivity.favorites.size() == 0) {
                    Toast.makeText(getContext(), "관심강의가 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    parse();
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cd = etCode.getText().toString();
                for (int i = 0; i < MainActivity.favorites.size(); i++) {
                    if(MainActivity.favorites.get(i).code.equals(cd)) {
                        Toast.makeText(getContext(), "이미 추가된강의 입니다.", Toast.LENGTH_SHORT).show();
                        etCode.setText("");
                        return;
                    }
                }
                if (MainActivity.favorites.size() >= 20) {
                    Toast.makeText(getContext(), "즐겨찾는 강의는 20개를 초과할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    etCode.setText("");
                    return;
                }
                add();
                etCode.setText("");
            }
        });

        etCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String cd = etCode.getText().toString();
                for (int i = 0; i < MainActivity.favorites.size(); i++) {
                    if(MainActivity.favorites.get(i).code.equals(cd)) {
                        Toast.makeText(getContext(), "이미 추가된 강의 입니다.", Toast.LENGTH_SHORT).show();
                        etCode.setText("");
                        return false;
                    }
                }
                if (MainActivity.favorites.size() >= 20) {
                    Toast.makeText(getContext(), "즐겨찾는 강의는 20개를 초과할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    etCode.setText("");
                    return false ;
                }
                add();
                etCode.setText("");
                return false;
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = etCode.getText().toString();
                for (int i = 0; i < MainActivity.favorites.size(); i++) {
                    if(MainActivity.favorites.get(i).code.equals(code)) {

                        SharedPreferences.Editor et0 = MainActivity.spNumber.edit();
                        SharedPreferences.Editor et1 = MainActivity.spCode.edit();
                        SharedPreferences.Editor et2 = MainActivity.spTitle.edit();
                        SharedPreferences.Editor et3 = MainActivity.spProf.edit();
                        SharedPreferences.Editor et4 = MainActivity.spTime.edit();

                        et1.remove(i+"");
                        et2.remove(i+"");
                        et3.remove(i+"");
                        et4.remove(i+"");

                        int index = MainActivity.favorites.size()-1;

                        if(i == index) {
                            et0.remove("howMany");
                            et0.putInt("howMany", index);
                        }
                        else {
                            et1.putString(i+"", MainActivity.favorites.get(index).code);
                            et2.putString(i+"", MainActivity.favorites.get(index).title);
                            et3.putString(i+"", MainActivity.favorites.get(index).prof);
                            et4.putString(i+"", MainActivity.favorites.get(index).time);

                            et1.remove(index+"");
                            et2.remove(index+"");
                            et3.remove(index+"");
                            et4.remove(index+"");

                            et0.remove("howMany");
                            et0.putInt("howMany", index);
                        }


                        et0.commit();
                        et1.commit();
                        et2.commit();
                        et3.commit();
                        et4.commit();

                        MainActivity.favorites.remove(i);
                        recyclerAdapter = new RecyclerAdapter(MainActivity.favorites);
                        recyclerView.setAdapter(recyclerAdapter);

                        Toast.makeText(getContext(), "강의(" + code + ")를 삭제했습니다.", Toast.LENGTH_SHORT).show();
                        etCode.setText("");
                        return;
                    }
                }
                Toast.makeText(getContext(), "입력한 과목코드가 즐겨찾기 목록에 없습니다.", Toast.LENGTH_SHORT).show();
                etCode.setText("");
            }
        });

        // 리사이클러뷰
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        // 진행 다이얼로그
        SpannableString msg =  new SpannableString("강의 검색 중..");
        msg.setSpan(new RelativeSizeSpan(1.3f), 0, msg.length(), 0);
        msg.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 0, msg.length(), 0);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(msg);
        progressDialog.setCanceledOnTouchOutside(false);

        parse();

        return view;
    }

    void add() {
        // 진행 다이얼로그 시작
        progressDialog.show();
        new Thread() {
            @Override
            public void run() {
                // 강의목록 파싱
                ParseList parseList = new ParseList();
                try {
                    boolean isError = false;
                    String code = etCode.getText().toString();
                    if (parseList.execute("FAVORITES", code).get()) {
                        // 잘못된 과목코드 일 때
                        if(!isValidCode) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), "해당 과목코드의 강의가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                            progressDialog.dismiss();
                            return;
                        }
                        // 강의인원 파싱
                        LinkedBlockingQueue<Runnable> lbq = new LinkedBlockingQueue<>(100);
                        ThreadPoolExecutor tpe = new ThreadPoolExecutor(10, 256, 1, TimeUnit.SECONDS, lbq);
                        int size = MainActivity.favorites.size();
                        ParseNumber[] parseNumber = new ParseNumber[size];
                        for (int i = 0; i < size; i++) {
                            parseNumber[i] = new ParseNumber(nowGrade, MainActivity.favorites.get(i).code);
                            parseNumber[i].executeOnExecutor(tpe);
                        }
                        for (int i = 0; i < size; i++) {
                            if (parseNumber[i].get()) {
                                MainActivity.favorites.get(i).setNumber(parseNumber[i].count, parseNumber[i].limit);
                            }
                            else {
                                isError = true;
                                break;
                            }
                        }
                    }
                    else {
                        isError = true;
                    }
                    // 오류가 발생했을 때
                    if (isError) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "네트워크 상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    // 오류가 발생하지 않았을 때
                    else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerAdapter = new RecyclerAdapter(MainActivity.favorites);
                                recyclerView.setAdapter(recyclerAdapter);

                                int index = MainActivity.favorites.size() - 1;
                                Lecture lec = MainActivity.favorites.get(index);

                                SharedPreferences.Editor et0 = MainActivity.spNumber.edit();
                                SharedPreferences.Editor et1 = MainActivity.spCode.edit();
                                SharedPreferences.Editor et2 = MainActivity.spTitle.edit();
                                SharedPreferences.Editor et3 = MainActivity.spProf.edit();
                                SharedPreferences.Editor et4 = MainActivity.spTime.edit();
                                et1.putString(index+"", lec.code);
                                et2.putString(index+"", lec.title);
                                et3.putString(index+"", lec.prof);
                                et4.putString(index+"", lec.time);
                                et0.remove("howMany");
                                et0.putInt("howMany", index + 1);
                                et0.commit();
                                et1.commit();
                                et2.commit();
                                et3.commit();
                                et4.commit();
                            }
                        });
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 진행 다이얼로그 종료
                progressDialog.dismiss();
            }
        }.start();
    }

    void parse() {
        // 진행 다이얼로그 시작
        progressDialog.show();
        new Thread() {
            @Override
            public void run() {
                // 강의목록 파싱
                ParseList parseList = new ParseList();
                try {
                    boolean isError = false;
                    // 강의인원 파싱
                    LinkedBlockingQueue<Runnable> lbq = new LinkedBlockingQueue<>(100);
                    ThreadPoolExecutor tpe = new ThreadPoolExecutor(10, 256, 1, TimeUnit.SECONDS, lbq);
                    int size = MainActivity.favorites.size();
                    ParseNumber[] parseNumber = new ParseNumber[size];
                    for (int i = 0; i < size; i++) {
                        parseNumber[i] = new ParseNumber(nowGrade, MainActivity.favorites.get(i).code);
                        parseNumber[i].executeOnExecutor(tpe);
                    }
                    for (int i = 0; i < size; i++) {
                        if (parseNumber[i].get()) {
                            MainActivity.favorites.get(i).setNumber(parseNumber[i].count, parseNumber[i].limit);
                        }
                        else {
                            isError = true;
                            break;
                        }
                    }
                    // 오류가 발생했을 때
                    if (isError) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "네트워크 상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    // 오류가 발생하지 않았을 때
                    else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerAdapter = new RecyclerAdapter(MainActivity.favorites);
                                recyclerView.setAdapter(recyclerAdapter);
                            }
                        });
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 진행 다이얼로그 종료
                progressDialog.dismiss();
            }
        }.start();
    }

}
