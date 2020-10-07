package com.onekus.onekus;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class Fragment_3 extends Fragment {


    static String name;

    int       basket[],  count[],  limit[],  seat[];
    TextView _basket[], _count[], _limit[], _seat[];

    EditText etCode;
    TextView       tvName;
    ImageButton btnSearch;
    ProgressDialog progressDialog;

    /*TextView tvFail;
    TextView tvFakeSeat;
    TextView tvRealSeat;
    TextView tvFakeRate;
    TextView tvRealRate;*/

    public Fragment_3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_3, container, false);

        name   = "";

        basket = new int[5];
        count  = new int[5];
        limit  = new int[5];
        seat   = new int[5];

        _basket = new TextView[5];
        _count  = new TextView[5];
        _limit  = new TextView[5];
        _seat   = new TextView[5];

        _basket[0] = view.findViewById(R.id._0a);
        _basket[1] = view.findViewById(R.id._1a);
        _basket[2] = view.findViewById(R.id._2a);
        _basket[3] = view.findViewById(R.id._3a);
        _basket[4] = view.findViewById(R.id._4a);
        _count[0]  = view.findViewById(R.id._0b);
        _count[1]  = view.findViewById(R.id._1b);
        _count[2]  = view.findViewById(R.id._2b);
        _count[3]  = view.findViewById(R.id._3b);
        _count[4]  = view.findViewById(R.id._4b);
        _limit[0]  = view.findViewById(R.id._0c);
        _limit[1]  = view.findViewById(R.id._1c);
        _limit[2]  = view.findViewById(R.id._2c);
        _limit[3]  = view.findViewById(R.id._3c);
        _limit[4]  = view.findViewById(R.id._4c);
        _seat[0]   = view.findViewById(R.id._0d);
        _seat[1]   = view.findViewById(R.id._1d);
        _seat[2]   = view.findViewById(R.id._2d);
        _seat[3]   = view.findViewById(R.id._3d);
        _seat[4]   = view.findViewById(R.id._4d);

        etCode    = view.findViewById(R.id.etCode);
        tvName    = view.findViewById(R.id.tvName);
        btnSearch = view.findViewById(R.id.btnSearch);

        etCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                parse();
                etCode.setText("");
                return false;
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parse();
                etCode.setText("");
            }
        });

        SpannableString msg =  new SpannableString("강의 검색 중..");
        msg.setSpan(new RelativeSizeSpan(1.3f), 0, msg.length(), 0);
        msg.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 0, msg.length(), 0);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(msg);
        progressDialog.setCanceledOnTouchOutside(false);

        /*tvFail     = view.findViewById(R.id.tvFail);
        tvFakeSeat = view.findViewById(R.id.tvFakeSeat);
        tvRealSeat = view.findViewById(R.id.tvRealSeat);
        tvFakeRate = view.findViewById(R.id.tvFakeRate);
        tvRealRate = view.findViewById(R.id.tvRealRate);*/

        return view;
    }

    void parse() {
        // 진행 다이얼로그 시작
        progressDialog.show();
        new Thread() {
            @Override
            public void run() {
                ParseList parseList = new ParseList();
                try {
                    boolean isError = false;
                    String code = etCode.getText().toString();
                    if (parseList.execute("CODE", code).get()) {
                        // 잘못된 과목코드 일 때
                        if (name.equals("error")) {
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
                        ParseNumber[] parseNumber = new ParseNumber[5];
                        parseNumber[1] = new ParseNumber("1학년", code);
                        parseNumber[2] = new ParseNumber("2학년", code);
                        parseNumber[3] = new ParseNumber("3학년", code);
                        parseNumber[4] = new ParseNumber("4학년", code);
                        parseNumber[1].executeOnExecutor(tpe);
                        parseNumber[2].executeOnExecutor(tpe);
                        parseNumber[3].executeOnExecutor(tpe);
                        parseNumber[4].executeOnExecutor(tpe);
                        int sumBasket = 0, sumCount = 0, sumLimit = 0, sumSeat = 0;
                        for (int i = 1; i < 5; i++) {
                            if (parseNumber[i].get()) {
                                sumBasket += basket[i] = parseNumber[i].basket;
                                sumCount  += count[i]  = parseNumber[i].count;
                                sumLimit  += limit[i]  = parseNumber[i].limit;
                                sumSeat   += seat[i]   = limit[i] - count[i];
                            }
                            else {
                                isError = true;
                                break;
                            }
                        }
                        basket[0] = sumBasket;
                        count[0]  = sumCount;
                        limit[0]  = sumLimit;
                        seat[0]   = sumSeat;
                    }
                    else {
                        isError = true;
                    }
                    // 오류가 발생했을 때
                    if (isError) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "네트워크 or 학교서버 지연", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    // 오류가 발생하지 않았을 때
                    else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvName.setText(name);
                                for (int i = 0; i < 5; i++) {
                                    _basket[i].setText(basket[i] + " 명");
                                    _count[i].setText(count[i]   + " 명");
                                    _limit[i].setText(limit[i]   + " 명");
                                    _seat[i].setText(seat[i]     + " 자리");
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
