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

public class Fragment_1 extends Fragment implements Button.OnClickListener {

    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;

    SpannableString spannableString;
    ProgressDialog progressDialog;

    Spinner spnDivision;
    Spinner spnGrade;
    Button btnSearchAll;
    Button btnSearchEmpty;

    boolean isSearchOnlyEmpty;

    public Fragment_1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        spannableString = new SpannableString("강의 검색 중..");
        spannableString.setSpan(new RelativeSizeSpan(1.3f), 0, spannableString.length(), 0);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 0, spannableString.length(), 0);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(spannableString);
        progressDialog.setCanceledOnTouchOutside(false);

        spnDivision = view.findViewById(R.id.spnDivision);
        spnGrade = view.findViewById(R.id.spnGrade);

        btnSearchAll = view.findViewById(R.id.btnSearchAll);
        btnSearchAll.setOnClickListener(this);
        btnSearchEmpty = view.findViewById(R.id.btnSearchEmpty);
        btnSearchEmpty.setOnClickListener(this);

        return view;
    }

    public boolean isDivisionSelected() {
        if(spnDivision.getSelectedItemPosition() == 0) {
            Toast.makeText(getContext(), "이수구분을 선택해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void setButtonColor() {
        if(isSearchOnlyEmpty) {
            btnSearchAll.setBackground(getResources().getDrawable(R.drawable.gray_round));
            btnSearchAll.setTextColor(getResources().getColor(R.color.colorDarkGray));
            btnSearchEmpty.setBackground(getResources().getDrawable(R.drawable.primary_round));
            btnSearchEmpty.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else {
            btnSearchAll.setBackground(getResources().getDrawable(R.drawable.primary_round));
            btnSearchAll.setTextColor(getResources().getColor(R.color.colorWhite));
            btnSearchEmpty.setBackground(getResources().getDrawable(R.drawable.gray_round));
            btnSearchEmpty.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    @Override
    public void onClick(View view) {
        if(isDivisionSelected()) {
            if(view.getId() == R.id.btnSearchAll) {
                isSearchOnlyEmpty = false;
            }
            else if(view.getId() == R.id.btnSearchEmpty){
                isSearchOnlyEmpty = true;
            }
            setButtonColor();
            parse();
        }
    }

    public void parse() {
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
                    if (parseList.execute(spnDivision.getSelectedItem().toString()).get()) {
                        int size = MainActivity.lectures.size();
                        // 강의의 수가 0개 일 때
                        if (size == 0) {
                            recyclerAdapter = new RecyclerAdapter(MainActivity.lectures);
                            recyclerView.setAdapter(recyclerAdapter);
                            Toast.makeText(getContext(), "강의가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            return;
                        }
                        // 강의인원 파싱
                        LinkedBlockingQueue<Runnable> lbq = new LinkedBlockingQueue<>(100);
                        ThreadPoolExecutor tpe = new ThreadPoolExecutor(10, 256, 1, TimeUnit.SECONDS, lbq);
                        ParseNumber[] parseNumber = new ParseNumber[size];
                        String grade = spnGrade.getSelectedItem().toString();
                        for (int i = 0; i < size; i++) {
                            parseNumber[i] = new ParseNumber(grade, MainActivity.lectures.get(i).getCode());
                            parseNumber[i].executeOnExecutor(tpe);
                        }
                        for (int i = 0; i < size; i++) {
                            if (parseNumber[i].get()) {
                                MainActivity.lectures.get(i).setPresent(parseNumber[i].count);
                                MainActivity.lectures.get(i).setLimit(parseNumber[i].limit);
                            } else {
                                isError = true;
                                break;
                            }
                        }
                    } else {
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
                                if (isSearchOnlyEmpty) {
                                    for (int i = MainActivity.lectures.size() - 1; i >= 0; i--) {
                                        if (MainActivity.lectures.get(i).getPresent() >= MainActivity.lectures.get(i).getLimit()) {
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
