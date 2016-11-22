package com.manateams.android.manateams.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.EditText;

import com.manateams.android.manateams.R;
import com.manateams.android.manateams.util.Constants;
import com.manateams.scraper.data.Assignment;
import com.manateams.scraper.data.Category;
import com.manateams.scraper.data.ClassGrades;
import com.manateams.scraper.data.GradeValue;
import com.manateams.scraper.util.Numeric;
import com.manateams.scraper.GradeCalc;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private ClassGrades grades;
    private Context context;

    public CategoryAdapter(Context context, ClassGrades grades) {
        this.context = context;
        this.grades = grades;
        grades.projectedAverage = -1;
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_assignment_category, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder viewHolder, int position) {
        viewHolder.setIsRecyclable(false);
        if (viewHolder.assignmentTable.getChildCount() == 0) {
            if (grades != null && grades.categories != null) {
                if (position >= grades.categories.length) {
                    viewHolder.titleText.setText(context.getString(R.string.misc_average));
                    if (grades.average != -1 && grades.projectedAverage == -1) {
                        viewHolder.weightText.setText(Integer.toString(grades.average));
                    } else if (grades.projectedAverage != -1){
                        viewHolder.weightText.setTextColor(context.getResources().getColor(R.color.red));
                        viewHolder.weightText.setText(Integer.toString(grades.projectedAverage));
                    } else {
                        viewHolder.weightText.setText("");
                    }
                    viewHolder.colorBar.setBackgroundColor(context.getResources().getColor(R.color.app_primary));

                    viewHolder.assignmentTable.setPadding(0, 0, 0, 0);
                } else {
                    Category category = grades.categories[position];
                    if (category != null) {
                        Assignment[] assignments = category.assignments;
                        if (assignments != null) {
                            viewHolder.titleText.setText(category.title);
                            // Set a different color
                            viewHolder.colorBar.setBackgroundColor(Color.parseColor(Constants.COLORS[position % Constants.COLORS.length]));

                            if (category.weight >= 0) {
                                viewHolder.weightText.setText(((int) category.weight) + "%");
                            } else {
                                viewHolder.weightText.setText(context.getString(R.string.text_card_category_assignment_weight_null));
                            }

                            for (int i = 0; i < assignments.length; i++) {
                                TableRow assignmentRow = new TableRow(context);
                                Assignment assignment = assignments[i];
                                if (assignment != null) {
                                    if (assignment.ptsEarned != null && assignment.ptsEarned.value != -1 && !assignment.isProjected) {
                                        LinearLayout row = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.row_assignment, null);
                                        TextView assignmentText = (TextView) row.findViewById(R.id.text_assignment);
                                        TextView gradeText = (TextView) row.findViewById(R.id.text_grade);
                                        assignmentText.setText(assignment.title);
                                        gradeText.setText(assignment.pointsString());
                                        assignmentRow.addView(row);
                                        viewHolder.assignmentTable.addView(assignmentRow);
                                    } else {
                                        LinearLayout row = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.row_assignment_editable, null);
                                        TextView assignmentText = (TextView) row.findViewById(R.id.text_assignment);

                                        final EditText gradeText = (EditText) row.findViewById(R.id.text_grade);
                                        //bring up the keyboard and make sure the cursor is at the end
                                        gradeText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                            @Override
                                            public void onFocusChange(View v, boolean hasFocus) {
                                                if (hasFocus) {
                                                    EditText e = (EditText) v;
                                                    String s = e.getText().toString();
                                                    e.setText("");
                                                    e.append(s);
                                                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                                    imm.showSoftInput(e, InputMethodManager.SHOW_IMPLICIT);

                                                }
                                            }
                                        });

                                        TextView.OnEditorActionListener listener = new EditorActionListener(context, this, position, i, assignment.ptsPossible);
                                        gradeText.setOnEditorActionListener(listener); //listen for actions in the EditText

                                        if (assignment.isProjected && assignment.ptsEarned != null && assignment.ptsEarned.value != -1) {
                                            gradeText.setText(Numeric.doubleToPrettyString(Math.round(assignment.ptsEarned.value_d*(assignment.ptsPossible/100))));
                                        }

                                        TextView ptsPossibleText = (TextView) row.findViewById(R.id.text_ptspossible);
                                        if (assignment.ptsPossible != 100) {
                                            ptsPossibleText.setText("/" + Numeric.doubleToPrettyString(assignment.ptsPossible));
                                            //listen for clicking on the ptspossible TextView
                                            ptsPossibleText.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    //focus the EditText and bring up the keyboard
                                                    LinearLayout parentRow = (LinearLayout) v.getParent();
                                                    EditText childEditText = (EditText) parentRow.getChildAt(1);
                                                    childEditText.requestFocus();
                                                    childEditText.setSelection(childEditText.getText().length());
                                                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                                    imm.showSoftInput(childEditText, InputMethodManager.SHOW_IMPLICIT);
                                                }
                                            });
                                        }

                                        assignmentText.setText(assignment.title);
                                        assignmentRow.addView(row);
                                        viewHolder.assignmentTable.addView(assignmentRow);
                                    }
                                }
                            }
                            // Add category average
                            TableRow averageRow = new TableRow(context);
                            LinearLayout row = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.row_assignment, null);
                            TextView assignmentText = (TextView) row.findViewById(R.id.text_assignment);
                            assignmentText.setText(context.getString(R.string.misc_average));
                            assignmentText.setTypeface(assignmentText.getTypeface(), Typeface.BOLD);
                            TextView gradeText = (TextView) row.findViewById(R.id.text_grade);
                            gradeText.setTypeface(assignmentText.getTypeface(), Typeface.BOLD);
                            if (category.average != null) {
                                gradeText.setText(String.valueOf(category.average.intValue()));
                            }
                            if (category.projectedAverage != null) {
                                gradeText.setTextColor(context.getResources().getColor(R.color.red));
                                gradeText.setText(String.valueOf(category.projectedAverage.intValue()));
                            }
                            averageRow.addView(row);
                            viewHolder.assignmentTable.addView(averageRow);
                        }
                    }
                }
            }
        } else {
            viewHolder.titleText.setText(context.getString(R.string.text_no_grades));
        }
    }

    @Override
    public int getItemCount() {
        if (grades != null && grades.categories != null) {
            return grades.categories.length + 1; //one more card for course average
        } else {
            return 0;
        }
    }

    public void updateProjectedGrade (int categoryIndex, int assignmentIndex, GradeValue newGrade) {
        if (newGrade == null) {
            grades.categories[categoryIndex].assignments[assignmentIndex].ptsEarned = new GradeValue("");
            grades.categories[categoryIndex].assignments[assignmentIndex].isProjected = false;
        } else {
            grades.categories[categoryIndex].assignments[assignmentIndex].ptsEarned = newGrade;
            grades.categories[categoryIndex].assignments[assignmentIndex].isProjected = true;
        }
    }

    public void updateProjectedAverages (int categoryIndex) {
        Category category = grades.categories[categoryIndex];
        if (!category.hasProjected()) {
            category.projectedAverage = null;
            grades.projectedAverage = GradeCalc.cycleAverage(grades).value;
            if (!grades.hasProjected()) {
                grades.projectedAverage = -1;
            }
        } else {
            category.projectedAverage = GradeCalc.categoryAverage(grades.categories[categoryIndex].assignments);
            grades.projectedAverage = GradeCalc.cycleAverage(grades).value;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleText;
        public TextView weightText;
        public LinearLayout colorBar;
        public TableLayout assignmentTable;

        public ViewHolder(View itemView) {
            super(itemView);
            titleText = (TextView) itemView.findViewById(R.id.text_title);
            weightText = (TextView) itemView.findViewById(R.id.text_weight);
            colorBar = (LinearLayout) itemView.findViewById(R.id.layout_title_color);
            assignmentTable = (TableLayout) itemView.findViewById(R.id.table_assignments);
        }
    }
}
