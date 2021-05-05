package com.credistudio.dailybudgettracker.Fragment;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.credistudio.dailybudgettracker.Adapter.AccountSpinnerAdapter;
import com.credistudio.dailybudgettracker.Adapter.CategorySpinnerAdapter;
import com.credistudio.dailybudgettracker.Adapter.ExpensesRVAdapter;
import com.credistudio.dailybudgettracker.Adapter.IncomeRVAdapter;
import com.credistudio.dailybudgettracker.Adapter.TransferRVAdapter;
import com.credistudio.dailybudgettracker.Classes.AccountDetails;
import com.credistudio.dailybudgettracker.Classes.Category;
import com.credistudio.dailybudgettracker.Classes.Expense;
import com.credistudio.dailybudgettracker.Classes.Transactions;
import com.credistudio.dailybudgettracker.Dagger.DaggerApplication;
import com.credistudio.dailybudgettracker.Dagger.DataManager;
import com.credistudio.dailybudgettracker.R;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import javax.inject.Inject;

public class FragmentReportFilterPager extends Fragment {
    private static final String ARG_POSITION = "position";
    ArrayList<AccountDetails> arrayListBank;
    ArrayList<AccountDetails> arrayListCredit;
    ArrayList<AccountDetails> arrayListDebit;
    ArrayList<AccountDetails> arrayListWallet;
    ArrayList<Category> catListIncome;
    ArrayList<Category> categoryList;
    @Inject
    DataManager dataManager;
    String date = "";
    String dateExpenseFrom = "";
    String dateExpenseTo = "";
    String dateIncomeFrom = "";
    String dateIncomeTo = "";
    String dateTransferFrom = "";
    String dateTransferTo = "";
    String expenseAccount = "";
    CardView expenseCardView_amount;
    String expenseCatId = "";
    ArrayList<Expense> expenseList;
    TextView headTvExpenseReport_fromDate;
    TextView headTvExpenseReport_toDate;
    TextView headTvIncomeReport_fromDate;
    TextView headTvIncomeReport_toDate;
    TextView headTvTransferReport_fromDate;
    TextView headTvTransferReport_toDate;
    String incomeAccount = "";
    CardView incomeCardView_amount;
    String incomeCatId = "";
    ArrayList<Expense> incomeList;
    LinearLayout linExpenseReport_noDataFound;
    LinearLayout linIncomeReport_noDataFound;
    LinearLayout linTransferReport_noDataFound;
    int mDay = 0;
    int mMonth = 0;
    int mYear = 0;
    private int position;
    RecyclerView rcv_expenseReport;
    RecyclerView rcv_incomeReport;
    RecyclerView rcv_transferReport;
    Spinner spinExpenseReport_account;
    Spinner spinExpenseReport_category;
    Spinner spinIncomeReport_account;
    Spinner spinIncomeReport_category;
    ArrayList<AccountDetails> spinListAccount;
    ArrayList<AccountDetails> spinListIncomeAccount;
    Spinner spinTransferReport_fromAccount;
    Spinner spinTransferReport_toAccount;
    String tranferAccountFrom = "";
    ArrayList<Transactions> transactionList;
    String transferAccountTo = "";
    CardView transferCardView_amount;
    TextView tvExpenseReport_fromDate;
    TextView tvExpenseReport_toDate;
    TextView tvExpenseReport_totalAmount;
    TextView tvIncomeReport_fromDate;
    TextView tvIncomeReport_toDate;
    TextView tvIncomeReport_totalAmount;
    TextView tvTransferReport_fromDate;
    TextView tvTransferReport_toDate;
    TextView tvTransferReport_totalAmount;

    public static FragmentReportFilterPager newInstance(int i) {
        FragmentReportFilterPager fragmentReportFilterPager = new FragmentReportFilterPager();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_POSITION, i);
        fragmentReportFilterPager.setArguments(bundle);
        return fragmentReportFilterPager;
    }

    public void onCreate(Bundle bundle) {
        setRetainInstance(true);
        super.onCreate(bundle);
        this.position = getArguments().getInt(ARG_POSITION);
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        String str;
        String str2;
        Date date2;
        View view;
        LayoutInflater layoutInflater2 = (LayoutInflater) getActivity().getSystemService("layout_inflater");
        ((DaggerApplication) getActivity().getApplication()).getComponent().inject(this);
        getAccountList();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar instance = Calendar.getInstance();
        this.mYear = instance.get(1);
        this.mMonth = instance.get(2);
        this.mDay = instance.get(5);
        if (this.mDay < 10) {
            StringBuilder sb = new StringBuilder();
            sb.append("0");
            sb.append(this.mDay);
            str = sb.toString();
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("");
            sb2.append(this.mDay);
            str = sb2.toString();
        }
        if (this.mMonth + 1 < 10) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("0");
            sb3.append(this.mMonth + 1);
            str2 = sb3.toString();
        } else {
            StringBuilder sb4 = new StringBuilder();
            sb4.append("");
            sb4.append(this.mMonth + 1);
            str2 = sb4.toString();
        }
        StringBuilder sb5 = new StringBuilder();
        sb5.append(str);
        sb5.append("-");
        sb5.append(str2);
        sb5.append("-");
        sb5.append(this.mYear);
        try {
            date2 = simpleDateFormat.parse(sb5.toString());
        } catch (ParseException e) {
            e.printStackTrace();
            date2 = null;
        }
        instance.setTime(date2);
        instance.add(6, -7);
        this.date = simpleDateFormat.format(instance.getTime());
        if (this.position == 0) {
            view = layoutInflater2.inflate(R.layout.item_report_filter_expense, null);
            this.rcv_expenseReport = (RecyclerView) view.findViewById(R.id.rcv_expenseReport);
            this.spinExpenseReport_category = (Spinner) view.findViewById(R.id.spinExpenseReport_category);
            this.spinExpenseReport_account = (Spinner) view.findViewById(R.id.spinExpenseReport_account);
            this.linExpenseReport_noDataFound = (LinearLayout) view.findViewById(R.id.linExpenseReport_noDataFound);
            this.tvExpenseReport_fromDate = (TextView) view.findViewById(R.id.tvExpenseReport_fromDate);
            this.tvExpenseReport_toDate = (TextView) view.findViewById(R.id.tvExpenseReport_toDate);
            this.headTvExpenseReport_fromDate = (TextView) view.findViewById(R.id.headTvExpenseReport_fromDate);
            this.headTvExpenseReport_toDate = (TextView) view.findViewById(R.id.headTvExpenseReport_toDate);
            this.tvExpenseReport_totalAmount = (TextView) view.findViewById(R.id.tvExpenseReport_totalAmount);
            this.expenseCardView_amount = (CardView) view.findViewById(R.id.expenseCardView_amount);
            TextView textView = this.tvExpenseReport_fromDate;
            StringBuilder sb6 = new StringBuilder();
            sb6.append(str);
            sb6.append("-");
            sb6.append(str2);
            sb6.append("-");
            sb6.append(this.mYear);
            textView.setText(sb6.toString());
            this.tvExpenseReport_toDate.setText(this.date);
            try {
                this.dateExpenseFrom = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(this.tvExpenseReport_fromDate.getText().toString()));
                this.dateExpenseTo = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(this.tvExpenseReport_toDate.getText().toString()));
                this.spinListAccount.add(0, new AccountDetails());
                this.spinExpenseReport_account.setAdapter(new AccountSpinnerAdapter(getActivity(), this.spinListAccount, R.layout.item_account));
                this.spinExpenseReport_account.setOnItemSelectedListener(new OnItemSelectedListener() {
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                        if (i != 0) {
                            AccountDetails accountDetails = (AccountDetails) adapterView.getItemAtPosition(i);
                            FragmentReportFilterPager.this.expenseAccount = accountDetails.getAccountName();
                            FragmentReportFilterPager.this.getFilterListExpense(FragmentReportFilterPager.this.dateExpenseFrom, FragmentReportFilterPager.this.dateExpenseTo, FragmentReportFilterPager.this.expenseAccount, FragmentReportFilterPager.this.expenseCatId);
                            return;
                        }
                        FragmentReportFilterPager.this.expenseAccount = "";
                        FragmentReportFilterPager.this.getFilterListExpense(FragmentReportFilterPager.this.dateExpenseFrom, FragmentReportFilterPager.this.dateExpenseTo, FragmentReportFilterPager.this.expenseAccount, FragmentReportFilterPager.this.expenseCatId);
                    }
                });
                this.categoryList = this.dataManager.getCategory();
                Collections.sort(this.categoryList, new Comparator<Category>() {
                    public int compare(Category category, Category category2) {
                        try {
                            return category.getCatName().compareTo(category2.getCatName());
                        } catch (Exception e) {
                            throw new IllegalArgumentException(e);
                        }
                    }
                });
                this.categoryList.add(0, new Category());
                this.spinExpenseReport_category.setAdapter(new CategorySpinnerAdapter(getActivity(), R.layout.item_account, this.categoryList));
                this.spinExpenseReport_category.setOnItemSelectedListener(new OnItemSelectedListener() {
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                        if (i != 0) {
                            Category category = (Category) adapterView.getItemAtPosition(i);
                            FragmentReportFilterPager.this.expenseCatId = String.valueOf(category.getCatId());
                            FragmentReportFilterPager.this.getFilterListExpense(FragmentReportFilterPager.this.dateExpenseFrom, FragmentReportFilterPager.this.dateExpenseTo, FragmentReportFilterPager.this.expenseAccount, FragmentReportFilterPager.this.expenseCatId);
                            return;
                        }
                        FragmentReportFilterPager.this.expenseCatId = "";
                        FragmentReportFilterPager.this.getFilterListExpense(FragmentReportFilterPager.this.dateExpenseFrom, FragmentReportFilterPager.this.dateExpenseTo, FragmentReportFilterPager.this.expenseAccount, FragmentReportFilterPager.this.expenseCatId);
                    }
                });
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            this.headTvExpenseReport_fromDate.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(FragmentReportFilterPager.this.getActivity(), new OnDateSetListener() {
                        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                            String str;
                            String str2;
                            if (i3 < 10) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("0");
                                sb.append(i3);
                                str = sb.toString();
                            } else {
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("");
                                sb2.append(i3);
                                str = sb2.toString();
                            }
                            int i4 = i2 + 1;
                            if (i4 < 10) {
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append("0");
                                sb3.append(i4);
                                str2 = sb3.toString();
                            } else {
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append("");
                                sb4.append(i4);
                                str2 = sb4.toString();
                            }
                            TextView textView = FragmentReportFilterPager.this.tvExpenseReport_fromDate;
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(str);
                            sb5.append("-");
                            sb5.append(str2);
                            sb5.append("-");
                            sb5.append(i);
                            textView.setText(sb5.toString());
                            try {
                                Date parse = new SimpleDateFormat("dd-MM-yyyy").parse(FragmentReportFilterPager.this.tvExpenseReport_fromDate.getText().toString());
                                FragmentReportFilterPager.this.dateExpenseFrom = new SimpleDateFormat("yyyy-MM-dd").format(parse);
                                FragmentReportFilterPager.this.getFilterListExpense(FragmentReportFilterPager.this.dateExpenseFrom, FragmentReportFilterPager.this.dateExpenseTo, FragmentReportFilterPager.this.expenseAccount, FragmentReportFilterPager.this.expenseCatId);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, FragmentReportFilterPager.this.mYear, FragmentReportFilterPager.this.mMonth, FragmentReportFilterPager.this.mDay);
                    datePickerDialog.show();
                }
            });
            this.tvExpenseReport_fromDate.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    FragmentReportFilterPager.this.headTvExpenseReport_fromDate.performClick();
                }
            });
            this.headTvExpenseReport_toDate.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(FragmentReportFilterPager.this.getActivity(), new OnDateSetListener() {
                        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                            String str;
                            String str2;
                            if (i3 < 10) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("0");
                                sb.append(i3);
                                str = sb.toString();
                            } else {
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("");
                                sb2.append(i3);
                                str = sb2.toString();
                            }
                            int i4 = i2 + 1;
                            if (i4 < 10) {
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append("0");
                                sb3.append(i4);
                                str2 = sb3.toString();
                            } else {
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append("");
                                sb4.append(i4);
                                str2 = sb4.toString();
                            }
                            TextView textView = FragmentReportFilterPager.this.tvExpenseReport_toDate;
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(str);
                            sb5.append("-");
                            sb5.append(str2);
                            sb5.append("-");
                            sb5.append(i);
                            textView.setText(sb5.toString());
                            try {
                                Date parse = new SimpleDateFormat("dd-MM-yyyy").parse(FragmentReportFilterPager.this.tvExpenseReport_toDate.getText().toString());
                                FragmentReportFilterPager.this.dateExpenseTo = new SimpleDateFormat("yyyy-MM-dd").format(parse);
                                FragmentReportFilterPager.this.getFilterListExpense(FragmentReportFilterPager.this.dateExpenseFrom, FragmentReportFilterPager.this.dateExpenseTo, FragmentReportFilterPager.this.expenseAccount, FragmentReportFilterPager.this.expenseCatId);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, FragmentReportFilterPager.this.mYear, FragmentReportFilterPager.this.mMonth, FragmentReportFilterPager.this.mDay);
                    datePickerDialog.show();
                }
            });
            this.tvExpenseReport_toDate.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    FragmentReportFilterPager.this.headTvExpenseReport_toDate.performClick();
                }
            });
        } else {
            view = null;
        }
        if (this.position == 1) {
            view = layoutInflater2.inflate(R.layout.item_report_filter_income, null);
            this.rcv_incomeReport = (RecyclerView) view.findViewById(R.id.rcv_incomeReport);
            this.spinIncomeReport_account = (Spinner) view.findViewById(R.id.spinIncomeReport_account);
            this.spinIncomeReport_category = (Spinner) view.findViewById(R.id.spinIncomeReport_category);
            this.linIncomeReport_noDataFound = (LinearLayout) view.findViewById(R.id.linIncomeReport_noDataFound);
            this.tvIncomeReport_fromDate = (TextView) view.findViewById(R.id.tvIncomeReport_fromDate);
            this.tvIncomeReport_toDate = (TextView) view.findViewById(R.id.tvIncomeReport_toDate);
            this.headTvIncomeReport_fromDate = (TextView) view.findViewById(R.id.headTvIncomeReport_fromDate);
            this.headTvIncomeReport_toDate = (TextView) view.findViewById(R.id.headTvIncomeReport_toDate);
            this.tvIncomeReport_totalAmount = (TextView) view.findViewById(R.id.tvIncomeReport_totalAmount);
            this.incomeCardView_amount = (CardView) view.findViewById(R.id.incomeCardView_amount);
            TextView textView2 = this.tvIncomeReport_fromDate;
            StringBuilder sb7 = new StringBuilder();
            sb7.append(str);
            sb7.append("-");
            sb7.append(str2);
            sb7.append("-");
            sb7.append(this.mYear);
            textView2.setText(sb7.toString());
            this.tvIncomeReport_toDate.setText(this.date);
            try {
                this.dateIncomeFrom = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(this.tvIncomeReport_fromDate.getText().toString()));
                this.dateIncomeTo = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(this.tvIncomeReport_toDate.getText().toString()));
                this.spinListIncomeAccount.add(0, new AccountDetails());
                this.spinIncomeReport_account.setAdapter(new AccountSpinnerAdapter(getActivity(), this.spinListIncomeAccount, R.layout.item_account));
                this.spinIncomeReport_account.setOnItemSelectedListener(new OnItemSelectedListener() {
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                        if (i != 0) {
                            AccountDetails accountDetails = (AccountDetails) adapterView.getItemAtPosition(i);
                            FragmentReportFilterPager.this.incomeAccount = accountDetails.getAccountName();
                            StringBuilder sb = new StringBuilder();
                            sb.append(".........................");
                            sb.append(FragmentReportFilterPager.this.incomeAccount);
                            sb.append("...............................");
                            Log.e("spinIncomeReportAccount", sb.toString());
                            FragmentReportFilterPager.this.getFilterListIncome(FragmentReportFilterPager.this.dateIncomeFrom, FragmentReportFilterPager.this.dateIncomeTo, FragmentReportFilterPager.this.incomeAccount, FragmentReportFilterPager.this.incomeCatId);
                            return;
                        }
                        FragmentReportFilterPager.this.incomeAccount = "";
                        FragmentReportFilterPager.this.getFilterListIncome(FragmentReportFilterPager.this.dateIncomeFrom, FragmentReportFilterPager.this.dateIncomeTo, FragmentReportFilterPager.this.incomeAccount, FragmentReportFilterPager.this.incomeCatId);
                    }
                });
                this.catListIncome = this.dataManager.getCategoryIncome();
                Collections.sort(this.catListIncome, new Comparator<Category>() {
                    public int compare(Category category, Category category2) {
                        try {
                            return category.getCatName().compareTo(category2.getCatName());
                        } catch (Exception e) {
                            throw new IllegalArgumentException(e);
                        }
                    }
                });
                this.catListIncome.add(0, new Category());
                this.spinIncomeReport_category.setAdapter(new CategorySpinnerAdapter(getActivity(), R.layout.item_account, this.catListIncome));
                this.spinIncomeReport_category.setOnItemSelectedListener(new OnItemSelectedListener() {
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                        if (i != 0) {
                            Category category = (Category) adapterView.getItemAtPosition(i);
                            FragmentReportFilterPager.this.incomeCatId = String.valueOf(category.getCatId());
                            StringBuilder sb = new StringBuilder();
                            sb.append(".........................");
                            sb.append(FragmentReportFilterPager.this.dateIncomeFrom);
                            sb.append("...................dateIncomeFrom............");
                            Log.e("IncomeReportCategory", sb.toString());
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(".........................");
                            sb2.append(FragmentReportFilterPager.this.dateIncomeTo);
                            sb2.append(".....................dateIncomeTo...........");
                            Log.e("IncomeReportCategory", sb2.toString());
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(".........................");
                            sb3.append(FragmentReportFilterPager.this.incomeAccount);
                            sb3.append(".....................incomeAccount..........");
                            Log.e("IncomeReportCategory", sb3.toString());
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append(".........................");
                            sb4.append(FragmentReportFilterPager.this.incomeCatId);
                            sb4.append(".......................incomeCatId........");
                            Log.e("IncomeReportCategory", sb4.toString());
                            FragmentReportFilterPager.this.getFilterListIncome(FragmentReportFilterPager.this.dateIncomeFrom, FragmentReportFilterPager.this.dateIncomeTo, FragmentReportFilterPager.this.incomeAccount, FragmentReportFilterPager.this.incomeCatId);
                            return;
                        }
                        FragmentReportFilterPager.this.incomeCatId = "";
                        FragmentReportFilterPager.this.getFilterListIncome(FragmentReportFilterPager.this.dateIncomeFrom, FragmentReportFilterPager.this.dateIncomeTo, FragmentReportFilterPager.this.incomeAccount, FragmentReportFilterPager.this.incomeCatId);
                    }
                });
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            this.headTvIncomeReport_fromDate.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(FragmentReportFilterPager.this.getActivity(), new OnDateSetListener() {
                        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                            String str;
                            String str2;
                            StringBuilder sb = new StringBuilder();
                            sb.append(".........................");
                            sb.append(i3);
                            sb.append("-");
                            int i4 = i2 + 1;
                            sb.append(i4);
                            sb.append("-");
                            sb.append(i);
                            sb.append("...................");
                            Log.e("Date pick", sb.toString());
                            if (i3 < 10) {
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("0");
                                sb2.append(i3);
                                str = sb2.toString();
                            } else {
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append("");
                                sb3.append(i3);
                                str = sb3.toString();
                            }
                            if (i4 < 10) {
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append("0");
                                sb4.append(i4);
                                str2 = sb4.toString();
                            } else {
                                StringBuilder sb5 = new StringBuilder();
                                sb5.append("");
                                sb5.append(i4);
                                str2 = sb5.toString();
                            }
                            TextView textView = FragmentReportFilterPager.this.tvIncomeReport_fromDate;
                            StringBuilder sb6 = new StringBuilder();
                            sb6.append(str);
                            sb6.append("-");
                            sb6.append(str2);
                            sb6.append("-");
                            sb6.append(i);
                            textView.setText(sb6.toString());
                            try {
                                Date parse = new SimpleDateFormat("dd-MM-yyyy").parse(FragmentReportFilterPager.this.tvIncomeReport_fromDate.getText().toString());
                                FragmentReportFilterPager.this.dateIncomeFrom = new SimpleDateFormat("yyyy-MM-dd").format(parse);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            FragmentReportFilterPager.this.getFilterListIncome(FragmentReportFilterPager.this.dateIncomeFrom, FragmentReportFilterPager.this.dateIncomeTo, FragmentReportFilterPager.this.incomeAccount, FragmentReportFilterPager.this.incomeCatId);
                        }
                    }, FragmentReportFilterPager.this.mYear, FragmentReportFilterPager.this.mMonth, FragmentReportFilterPager.this.mDay);
                    datePickerDialog.show();
                }
            });
            this.tvIncomeReport_fromDate.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    FragmentReportFilterPager.this.headTvIncomeReport_fromDate.performClick();
                }
            });
            this.headTvIncomeReport_toDate.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(FragmentReportFilterPager.this.getActivity(), new OnDateSetListener() {
                        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                            String str;
                            String str2;
                            if (i3 < 10) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("0");
                                sb.append(i3);
                                str = sb.toString();
                            } else {
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("");
                                sb2.append(i3);
                                str = sb2.toString();
                            }
                            int i4 = i2 + 1;
                            if (i4 < 10) {
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append("0");
                                sb3.append(i4);
                                str2 = sb3.toString();
                            } else {
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append("");
                                sb4.append(i4);
                                str2 = sb4.toString();
                            }
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(".........................");
                            sb5.append(i3);
                            sb5.append("-");
                            sb5.append(str2);
                            sb5.append("-");
                            sb5.append(i);
                            sb5.append("...................");
                            Log.e("Date pick", sb5.toString());
                            TextView textView = FragmentReportFilterPager.this.tvIncomeReport_toDate;
                            StringBuilder sb6 = new StringBuilder();
                            sb6.append(str);
                            sb6.append("-");
                            sb6.append(str2);
                            sb6.append("-");
                            sb6.append(i);
                            textView.setText(sb6.toString());
                            try {
                                Date parse = new SimpleDateFormat("dd-MM-yyyy").parse(FragmentReportFilterPager.this.tvIncomeReport_toDate.getText().toString());
                                FragmentReportFilterPager.this.dateIncomeTo = new SimpleDateFormat("yyyy-MM-dd").format(parse);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            FragmentReportFilterPager.this.getFilterListIncome(FragmentReportFilterPager.this.dateIncomeFrom, FragmentReportFilterPager.this.dateIncomeTo, FragmentReportFilterPager.this.incomeAccount, FragmentReportFilterPager.this.incomeCatId);
                        }
                    }, FragmentReportFilterPager.this.mYear, FragmentReportFilterPager.this.mMonth, FragmentReportFilterPager.this.mDay);
                    datePickerDialog.show();
                }
            });
            this.tvIncomeReport_toDate.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    FragmentReportFilterPager.this.headTvIncomeReport_toDate.performClick();
                }
            });
        }
        if (this.position == 2) {
            view = layoutInflater2.inflate(R.layout.item_report_filter_transfer, null);
            this.tvTransferReport_fromDate = (TextView) view.findViewById(R.id.tvTransferReport_fromDate);
            this.tvTransferReport_toDate = (TextView) view.findViewById(R.id.tvTransferReport_toDate);
            this.headTvTransferReport_fromDate = (TextView) view.findViewById(R.id.headTvTransferReport_fromDate);
            this.headTvTransferReport_toDate = (TextView) view.findViewById(R.id.headTvTransferReport_toDate);
            this.tvTransferReport_totalAmount = (TextView) view.findViewById(R.id.tvTransferReport_totalAmount);
            this.spinTransferReport_toAccount = (Spinner) view.findViewById(R.id.spinTransferReport_toAccount);
            this.spinTransferReport_fromAccount = (Spinner) view.findViewById(R.id.spinTransferReport_fromAccount);
            this.rcv_transferReport = (RecyclerView) view.findViewById(R.id.rcv_transferReport);
            this.linTransferReport_noDataFound = (LinearLayout) view.findViewById(R.id.linTransferReport_noDataFound);
            this.transferCardView_amount = (CardView) view.findViewById(R.id.transferCardView_amount);
            TextView textView3 = this.tvTransferReport_fromDate;
            StringBuilder sb8 = new StringBuilder();
            sb8.append(str);
            sb8.append("-");
            sb8.append(str2);
            sb8.append("-");
            sb8.append(this.mYear);
            textView3.setText(sb8.toString());
            this.tvTransferReport_toDate.setText(this.date);
            try {
                this.dateTransferFrom = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(this.tvTransferReport_fromDate.getText().toString()));
                this.dateTransferTo = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(this.tvTransferReport_toDate.getText().toString()));
            } catch (Exception e4) {
                e4.printStackTrace();
            }
            this.headTvTransferReport_fromDate.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(FragmentReportFilterPager.this.getActivity(), new OnDateSetListener() {
                        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                            String str;
                            String str2;
                            StringBuilder sb = new StringBuilder();
                            sb.append(".........................");
                            sb.append(i3);
                            sb.append("-");
                            int i4 = i2 + 1;
                            sb.append(i4);
                            sb.append("-");
                            sb.append(i);
                            sb.append("...................");
                            Log.e("Date pick", sb.toString());
                            if (i3 < 10) {
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("0");
                                sb2.append(i3);
                                str = sb2.toString();
                            } else {
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append("");
                                sb3.append(i3);
                                str = sb3.toString();
                            }
                            if (i4 < 10) {
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append("0");
                                sb4.append(i4);
                                str2 = sb4.toString();
                            } else {
                                StringBuilder sb5 = new StringBuilder();
                                sb5.append("");
                                sb5.append(i4);
                                str2 = sb5.toString();
                            }
                            TextView textView = FragmentReportFilterPager.this.tvTransferReport_fromDate;
                            StringBuilder sb6 = new StringBuilder();
                            sb6.append(str);
                            sb6.append("-");
                            sb6.append(str2);
                            sb6.append("-");
                            sb6.append(i);
                            textView.setText(sb6.toString());
                            try {
                                Date parse = new SimpleDateFormat("dd-MM-yyyy").parse(FragmentReportFilterPager.this.tvTransferReport_fromDate.getText().toString());
                                FragmentReportFilterPager.this.dateTransferFrom = new SimpleDateFormat("yyyy-MM-dd").format(parse);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            FragmentReportFilterPager.this.getFilterListTransfer(FragmentReportFilterPager.this.dateTransferFrom, FragmentReportFilterPager.this.dateTransferTo, FragmentReportFilterPager.this.tranferAccountFrom, FragmentReportFilterPager.this.transferAccountTo);
                        }
                    }, FragmentReportFilterPager.this.mYear, FragmentReportFilterPager.this.mMonth, FragmentReportFilterPager.this.mDay);
                    datePickerDialog.show();
                }
            });
            this.tvTransferReport_fromDate.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    FragmentReportFilterPager.this.headTvTransferReport_fromDate.performClick();
                }
            });
            this.headTvTransferReport_toDate.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(FragmentReportFilterPager.this.getActivity(), new OnDateSetListener() {
                        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                            String str;
                            String str2;
                            if (i3 < 10) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("0");
                                sb.append(i3);
                                str = sb.toString();
                            } else {
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("");
                                sb2.append(i3);
                                str = sb2.toString();
                            }
                            int i4 = i2 + 1;
                            if (i4 < 10) {
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append("0");
                                sb3.append(i4);
                                str2 = sb3.toString();
                            } else {
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append("");
                                sb4.append(i4);
                                str2 = sb4.toString();
                            }
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(".........................");
                            sb5.append(i3);
                            sb5.append("-");
                            sb5.append(str2);
                            sb5.append("-");
                            sb5.append(i);
                            sb5.append("...................");
                            Log.e("Date pick", sb5.toString());
                            TextView textView = FragmentReportFilterPager.this.tvTransferReport_toDate;
                            StringBuilder sb6 = new StringBuilder();
                            sb6.append(str);
                            sb6.append("-");
                            sb6.append(str2);
                            sb6.append("-");
                            sb6.append(i);
                            textView.setText(sb6.toString());
                            try {
                                Date parse = new SimpleDateFormat("dd-MM-yyyy").parse(FragmentReportFilterPager.this.tvTransferReport_toDate.getText().toString());
                                FragmentReportFilterPager.this.dateTransferTo = new SimpleDateFormat("yyyy-MM-dd").format(parse);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            FragmentReportFilterPager.this.getFilterListTransfer(FragmentReportFilterPager.this.dateTransferFrom, FragmentReportFilterPager.this.dateTransferTo, FragmentReportFilterPager.this.tranferAccountFrom, FragmentReportFilterPager.this.transferAccountTo);
                        }
                    }, FragmentReportFilterPager.this.mYear, FragmentReportFilterPager.this.mMonth, FragmentReportFilterPager.this.mDay);
                    datePickerDialog.show();
                }
            });
            this.tvTransferReport_toDate.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    FragmentReportFilterPager.this.headTvTransferReport_toDate.performClick();
                }
            });
            this.spinListIncomeAccount.add(0, new AccountDetails());
            AccountSpinnerAdapter accountSpinnerAdapter = new AccountSpinnerAdapter(getActivity(), this.spinListIncomeAccount, R.layout.item_account);
            this.spinTransferReport_fromAccount.setAdapter(accountSpinnerAdapter);
            this.spinTransferReport_toAccount.setAdapter(accountSpinnerAdapter);
            this.spinTransferReport_toAccount.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                    if (i != 0) {
                        AccountDetails accountDetails = (AccountDetails) adapterView.getItemAtPosition(i);
                        FragmentReportFilterPager.this.transferAccountTo = accountDetails.getAccountName();
                        FragmentReportFilterPager.this.getFilterListTransfer(FragmentReportFilterPager.this.dateTransferFrom, FragmentReportFilterPager.this.dateTransferTo, FragmentReportFilterPager.this.tranferAccountFrom, FragmentReportFilterPager.this.transferAccountTo);
                        return;
                    }
                    FragmentReportFilterPager.this.transferAccountTo = "";
                    FragmentReportFilterPager.this.getFilterListTransfer(FragmentReportFilterPager.this.dateTransferFrom, FragmentReportFilterPager.this.dateTransferTo, FragmentReportFilterPager.this.tranferAccountFrom, FragmentReportFilterPager.this.transferAccountTo);
                }
            });
            this.spinTransferReport_fromAccount.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                    if (i != 0) {
                        AccountDetails accountDetails = (AccountDetails) adapterView.getItemAtPosition(i);
                        FragmentReportFilterPager.this.tranferAccountFrom = accountDetails.getAccountName();
                        FragmentReportFilterPager.this.getFilterListTransfer(FragmentReportFilterPager.this.dateTransferFrom, FragmentReportFilterPager.this.dateTransferTo, FragmentReportFilterPager.this.tranferAccountFrom, FragmentReportFilterPager.this.transferAccountTo);
                        return;
                    }
                    FragmentReportFilterPager.this.tranferAccountFrom = "";
                    FragmentReportFilterPager.this.getFilterListTransfer(FragmentReportFilterPager.this.dateTransferFrom, FragmentReportFilterPager.this.dateTransferTo, FragmentReportFilterPager.this.tranferAccountFrom, FragmentReportFilterPager.this.transferAccountTo);
                }
            });
        }
        return view;
    }

    public void getAccountList() {
        try {
            this.arrayListBank = this.dataManager.getBank();
            this.arrayListWallet = this.dataManager.gettWallet();
            this.arrayListCredit = this.dataManager.getCredit();
            String cash = this.dataManager.getCash();
            this.spinListAccount = new ArrayList<>();
            this.spinListIncomeAccount = new ArrayList<>();
            this.spinListAccount.clear();
            AccountDetails accountDetails = new AccountDetails();
            accountDetails.setAccountId(0);
            accountDetails.setAccountName("Cash");
            accountDetails.setTotalAmount(cash);
            accountDetails.setType("Cash");
            this.spinListAccount.add(0, accountDetails);
            this.spinListIncomeAccount.add(0, accountDetails);
            if (this.arrayListBank != null) {
                for (int i = 0; i < this.arrayListBank.size(); i++) {
                    AccountDetails accountDetails2 = new AccountDetails();
                    accountDetails2.setAccountId(((AccountDetails) this.arrayListBank.get(i)).getBankId());
                    accountDetails2.setAccountName(((AccountDetails) this.arrayListBank.get(i)).getBankName());
                    accountDetails2.setTotalAmount(((AccountDetails) this.arrayListBank.get(i)).getInitialBalance());
                    accountDetails2.setAccountType(((AccountDetails) this.arrayListBank.get(i)).getAccountType());
                    accountDetails2.setType("Bank Account");
                    this.spinListAccount.add(this.spinListAccount.size(), accountDetails2);
                    this.spinListIncomeAccount.add(this.spinListIncomeAccount.size(), accountDetails2);
                }
            }
            if (this.arrayListWallet != null) {
                for (int i2 = 0; i2 < this.arrayListWallet.size(); i2++) {
                    AccountDetails accountDetails3 = new AccountDetails();
                    accountDetails3.setAccountId(((AccountDetails) this.arrayListWallet.get(i2)).geteWalletId());
                    accountDetails3.setAccountName(((AccountDetails) this.arrayListWallet.get(i2)).geteWalletName());
                    accountDetails3.setTotalAmount(((AccountDetails) this.arrayListWallet.get(i2)).getEwalletBalance());
                    accountDetails3.setType("E-wallet");
                    this.spinListAccount.add(this.spinListAccount.size(), accountDetails3);
                    this.spinListIncomeAccount.add(this.spinListIncomeAccount.size(), accountDetails3);
                }
            }
            if (this.arrayListCredit != null) {
                for (int i3 = 0; i3 < this.arrayListCredit.size(); i3++) {
                    AccountDetails accountDetails4 = new AccountDetails();
                    accountDetails4.setAccountId(((AccountDetails) this.arrayListCredit.get(i3)).getCreditId());
                    accountDetails4.setAccountName(((AccountDetails) this.arrayListCredit.get(i3)).getCreditCardName());
                    accountDetails4.setTotalAmount(((AccountDetails) this.arrayListCredit.get(i3)).getCreditCardBalance());
                    accountDetails4.setType("Credit Card");
                    this.spinListAccount.add(this.spinListAccount.size(), accountDetails4);
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append(".......");
            sb.append(this.spinListAccount.size());
            sb.append("..............");
            Log.e(".spinBank size...", sb.toString());
            for (int i4 = 0; i4 < this.spinListAccount.size(); i4++) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("<<<<<<<<<<<<<<<<<<<<................");
                sb2.append(((AccountDetails) this.spinListAccount.get(i4)).getAccountName());
                sb2.append("........>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                Log.e("....merged list......", sb2.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getFilterListExpense(String str, String str2, String str3, String str4) {
        try {
            Calendar instance = Calendar.getInstance();
            this.mYear = instance.get(1);
            this.mMonth = instance.get(2);
            this.mDay = instance.get(5);
            StringBuilder sb = new StringBuilder();
            sb.append(this.mDay);
            sb.append("-");
            sb.append(this.mMonth + 1);
            sb.append("-");
            sb.append(this.mYear);
            sb.toString();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("..........date1..........");
            sb2.append(str);
            sb2.append(".........RP..........");
            Log.e("Expense date1", sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append("..........toDate1..........");
            sb3.append(str2);
            sb3.append(".........RP..........");
            Log.e("Expense toDate1", sb3.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append("..........account..........");
            sb4.append(str3);
            sb4.append(".........RP..........");
            Log.e("Expense account", sb4.toString());
            StringBuilder sb5 = new StringBuilder();
            sb5.append("..........category1..........");
            sb5.append(str4);
            sb5.append(".........RP..........");
            Log.e("Expense category1", sb5.toString());
            this.expenseList = this.dataManager.getfilterListExpense(str, str2, str3, str4);
            if (this.expenseList == null) {
                this.rcv_expenseReport.setVisibility(8);
                this.expenseCardView_amount.setVisibility(8);
                this.linExpenseReport_noDataFound.setVisibility(0);
            } else if (this.expenseList.size() != 0) {
                Collections.sort(this.expenseList, new Comparator<Expense>() {
                    DateFormat f = new SimpleDateFormat("dd/MM/yyyy");

                    public int compare(Expense expense, Expense expense2) {
                        try {
                            return this.f.parse(expense.getDate().replace("-", "/")).compareTo(this.f.parse(expense2.getDate().replace("-", "/")));
                        } catch (ParseException e) {
                            throw new IllegalArgumentException(e);
                        }
                    }
                });
                Collections.reverse(this.expenseList);
                for (int i = 0; i < this.expenseList.size(); i++) {
                }
                Double.valueOf(0.0d);
                Double valueOf = Double.valueOf(0.0d);
                for (int i2 = 0; i2 < this.expenseList.size(); i2++) {
                    Double valueOf2 = Double.valueOf(((Expense) this.expenseList.get(i2)).getAmount());
                    if (valueOf.doubleValue() == 0.0d) {
                        valueOf = valueOf2;
                    } else {
                        valueOf = Double.valueOf(valueOf.doubleValue() + valueOf2.doubleValue());
                    }
                }
                TextView textView = this.tvExpenseReport_totalAmount;
                StringBuilder sb6 = new StringBuilder();
                sb6.append(valueOf);
                sb6.append("");
                textView.setText(sb6.toString());
                this.expenseCardView_amount.setVisibility(0);
                this.rcv_expenseReport.setVisibility(0);
                this.linExpenseReport_noDataFound.setVisibility(8);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(1);
                this.rcv_expenseReport.setLayoutManager(linearLayoutManager);
                this.rcv_expenseReport.setAdapter(new ExpensesRVAdapter(getActivity(), this.expenseList, this.spinListAccount, this.categoryList));
            } else {
                this.rcv_expenseReport.setVisibility(8);
                this.expenseCardView_amount.setVisibility(8);
                this.linExpenseReport_noDataFound.setVisibility(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getFilterListIncome(String str, String str2, String str3, String str4) {
        try {
            Calendar instance = Calendar.getInstance();
            this.mYear = instance.get(1);
            this.mMonth = instance.get(2);
            this.mDay = instance.get(5);
            StringBuilder sb = new StringBuilder();
            sb.append(this.mDay);
            sb.append("-");
            sb.append(this.mMonth + 1);
            sb.append("-");
            sb.append(this.mYear);
            sb.toString();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("..........date1..........");
            sb2.append(str);
            sb2.append(".........RPI..........");
            Log.e("Income date1", sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append("..........dateIncomeTo..........");
            sb3.append(str2);
            sb3.append(".........RPI..........");
            Log.e("Income dateIncomeTo", sb3.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append("..........account..........");
            sb4.append(str3);
            sb4.append(".........RPI..........");
            Log.e("Income account", sb4.toString());
            StringBuilder sb5 = new StringBuilder();
            sb5.append("..........category..........");
            sb5.append(str4);
            sb5.append(".........RPI..........");
            Log.e("Income account", sb5.toString());
            this.incomeList = this.dataManager.getfilterListIncome(str, str2, str3, str4);
            if (this.incomeList == null) {
                this.rcv_incomeReport.setVisibility(8);
                this.linIncomeReport_noDataFound.setVisibility(0);
                this.incomeCardView_amount.setVisibility(8);
            } else if (this.incomeList.size() != 0) {
                Collections.sort(this.incomeList, new Comparator<Expense>() {
                    DateFormat f = new SimpleDateFormat("dd/MM/yyyy");

                    public int compare(Expense expense, Expense expense2) {
                        try {
                            return this.f.parse(expense.getDate().replace("-", "/")).compareTo(this.f.parse(expense2.getDate().replace("-", "/")));
                        } catch (ParseException e) {
                            throw new IllegalArgumentException(e);
                        }
                    }
                });
                Collections.reverse(this.incomeList);
                Double.valueOf(0.0d);
                Double valueOf = Double.valueOf(0.0d);
                for (int i = 0; i < this.incomeList.size(); i++) {
                    Double valueOf2 = Double.valueOf(((Expense) this.incomeList.get(i)).getAmount());
                    if (valueOf.doubleValue() == 0.0d) {
                        valueOf = valueOf2;
                    } else {
                        valueOf = Double.valueOf(valueOf.doubleValue() + valueOf2.doubleValue());
                    }
                }
                TextView textView = this.tvIncomeReport_totalAmount;
                StringBuilder sb6 = new StringBuilder();
                sb6.append(valueOf);
                sb6.append("");
                textView.setText(sb6.toString());
                this.incomeCardView_amount.setVisibility(0);
                this.rcv_incomeReport.setVisibility(0);
                this.linIncomeReport_noDataFound.setVisibility(8);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(1);
                this.rcv_incomeReport.setLayoutManager(linearLayoutManager);
                this.rcv_incomeReport.setAdapter(new IncomeRVAdapter(getActivity(), this.incomeList, this.spinListIncomeAccount, this.catListIncome));
            } else {
                this.rcv_incomeReport.setVisibility(8);
                this.linIncomeReport_noDataFound.setVisibility(0);
                this.incomeCardView_amount.setVisibility(8);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getFilterListTransfer(String str, String str2, String str3, String str4) {
        String str5 = "transfer str_date";
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("..........dateTransferFrom..........");
            sb.append(this.dateTransferFrom);
            sb.append(".........RPI..........");
            Log.e(str5, sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("..........dateTransferTo..........");
            sb2.append(this.dateTransferTo);
            sb2.append(".........RPI..........");
            Log.e("transfer date1", sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append("..........transferAccountTo..........");
            sb3.append(this.transferAccountTo);
            sb3.append(".........RPI..........");
            Log.e("transfer dateIncomeTo", sb3.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append("..........tranferAccountFrom..........");
            sb4.append(this.tranferAccountFrom);
            sb4.append(".........RPI..........");
            Log.e("transfer category1", sb4.toString());
            this.transactionList = this.dataManager.getTransactionFilters(this.dateTransferFrom, this.dateTransferTo, this.tranferAccountFrom, this.transferAccountTo);
            if (this.transactionList == null) {
                this.rcv_transferReport.setVisibility(8);
                this.transferCardView_amount.setVisibility(8);
                this.linTransferReport_noDataFound.setVisibility(0);
            } else if (this.transactionList.size() != 0) {
                Collections.sort(this.transactionList, new Comparator<Transactions>() {
                    DateFormat f = new SimpleDateFormat("dd/MM/yyyy");

                    public int compare(Transactions transactions, Transactions transactions2) {
                        try {
                            return this.f.parse(transactions.getDate().replace("-", "/")).compareTo(this.f.parse(transactions2.getDate().replace("-", "/")));
                        } catch (ParseException e) {
                            throw new IllegalArgumentException(e);
                        }
                    }
                });
                Collections.reverse(this.transactionList);
                Double.valueOf(0.0d);
                Double valueOf = Double.valueOf(0.0d);
                for (int i = 0; i < this.transactionList.size(); i++) {
                    Double valueOf2 = Double.valueOf(((Transactions) this.transactionList.get(i)).getAmount());
                    if (valueOf.doubleValue() == 0.0d) {
                        valueOf = valueOf2;
                    } else {
                        valueOf = Double.valueOf(valueOf.doubleValue() + valueOf2.doubleValue());
                    }
                }
                TextView textView = this.tvTransferReport_totalAmount;
                StringBuilder sb5 = new StringBuilder();
                sb5.append(valueOf);
                sb5.append("");
                textView.setText(sb5.toString());
                this.transferCardView_amount.setVisibility(0);
                this.rcv_transferReport.setVisibility(0);
                this.linTransferReport_noDataFound.setVisibility(8);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(1);
                this.rcv_transferReport.setLayoutManager(linearLayoutManager);
                this.rcv_transferReport.setAdapter(new TransferRVAdapter(getActivity(), this.transactionList, this.spinListIncomeAccount));
            } else {
                this.rcv_transferReport.setVisibility(8);
                this.transferCardView_amount.setVisibility(8);
                this.linTransferReport_noDataFound.setVisibility(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
