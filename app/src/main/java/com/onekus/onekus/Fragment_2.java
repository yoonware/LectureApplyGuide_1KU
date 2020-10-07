package com.onekus.onekus;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_2 extends Fragment {

    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    RecyclerAdapter     recyclerAdapter;
    ProgressDialog progressDialog;

    Spinner spnDiv;
    Spinner spnMaj;
    Spinner spnGrade;
    Button btnAll;
    Button  btnEmpty;

    boolean isOnlyEmpty;

    public Fragment_2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);
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
        // 스피너
        spnMaj = view.findViewById(R.id.spnMaj);
        spnDiv = view.findViewById(R.id.spnDiv);
        spnGrade = view.findViewById(R.id.spnGrade);
        // 버튼 : 모든강의 보기
        btnAll = view.findViewById(R.id.btnAll);
        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spnMaj.getSelectedItem().toString().equals("수강학과 선택") || spnDiv.getSelectedItem().toString().equals("이수구분 선택")) {
                    Toast.makeText(getContext(), "수강학과와 이수구분을 모두 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    btnAll.setBackground(getResources().getDrawable(R.drawable.primary_round));
                    btnAll.setTextColor(getResources().getColor(R.color.colorWhite));
                    btnEmpty.setBackground(getResources().getDrawable(R.drawable.gray_round));
                    btnEmpty.setTextColor(getResources().getColor(R.color.colorDarkGray));
                    isOnlyEmpty = false;
                    parse();
                }
            }
        });
        // 버튼 : 빈 강의만 보기
        btnEmpty = view.findViewById(R.id.btnEmpty);
        btnEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spnMaj.getSelectedItem().toString().equals("수강학과 선택") || spnDiv.getSelectedItem().toString().equals("이수구분 선택")) {
                    Toast.makeText(getContext(), "수강학과와 이수구분을 모두 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    btnEmpty.setBackground(getResources().getDrawable(R.drawable.primary_round));
                    btnEmpty.setTextColor(getResources().getColor(R.color.colorWhite));
                    btnAll.setBackground(getResources().getDrawable(R.drawable.gray_round));
                    btnAll.setTextColor(getResources().getColor(R.color.colorDarkGray));
                    isOnlyEmpty = true;
                    parse();
                }
            }
        });
        return view;
    }

    void parse() {
        // 진행 다이얼로그 시작
        progressDialog.show();
        // 리스트 초기화
        MainActivity.lectures = new ArrayList<>();
        new Thread() {
            @Override
            public void run() {
                // 강의목록 파싱
                ParseList parseList = new ParseList();
                try {
                    boolean isError = false;
                    if (parseList.execute(spnDiv.getSelectedItem().toString(), getResources().getStringArray(R.array.majCode)[spnMaj.getSelectedItemPosition()]).get()) {
                        int size = MainActivity.lectures.size();
                        // 강의의 수가 0개 일 때
                        if(size == 0) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    recyclerAdapter = new RecyclerAdapter(MainActivity.lectures);
                                    recyclerView.setAdapter(recyclerAdapter);
                                    Toast.makeText(getContext(), "강의가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                            progressDialog.dismiss();
                            return;
                        }
                        // 강의인원 파싱
                        LinkedBlockingQueue<Runnable> lbq = new LinkedBlockingQueue<>(100);
                        ThreadPoolExecutor tpe = new ThreadPoolExecutor(10, 256, 1, TimeUnit.SECONDS, lbq);
                        ParseNumber[] parseNumber = new ParseNumber[size];
                        String grade = spnGrade.getSelectedItem().toString();
                        for (int i = 0; i < size; i++) {
                            parseNumber[i] = new ParseNumber(grade, MainActivity.lectures.get(i).code);
                            parseNumber[i].executeOnExecutor(tpe);
                        }
                        for (int i = 0; i < size; i++) {
                            if (parseNumber[i].get()) {
                                MainActivity.lectures.get(i).setNumber(parseNumber[i].count, parseNumber[i].limit);
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
                                if (isOnlyEmpty) {
                                    for (int i = MainActivity.lectures.size() - 1; i >= 0; i--) {
                                        if (MainActivity.lectures.get(i).count >= MainActivity.lectures.get(i).limit) {
                                            MainActivity.lectures.remove(i);
                                        }
                                    }
                                }
                                recyclerAdapter = new RecyclerAdapter(MainActivity.lectures);
                                recyclerView.setAdapter(recyclerAdapter);
                                if (MainActivity.lectures.size() == 0) {
                                    Toast.makeText(getContext(), "빈 강의가 없습니다.", Toast.LENGTH_SHORT).show();
                                }
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
