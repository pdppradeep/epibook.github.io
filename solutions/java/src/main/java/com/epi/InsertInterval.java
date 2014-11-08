package com.epi;

import com.epi.utils.Interval;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InsertInterval {
  // @include
  public static List<Interval> insertInterval(List<Interval> intervals,
                                              Interval newInterval) {
    int i = 0;
    List<Interval> result = new ArrayList<>();
    // Inserts intervals appeared before newInterval.
    while (i < intervals.size() && newInterval.left > intervals.get(i).right) {
      result.add(intervals.get(i++));
    }

    // Merges intervals that overlap with newInterval.
    while (i < intervals.size() && newInterval.right >= intervals.get(i).left) {
      newInterval = new Interval(Math.min(newInterval.left,
          intervals.get(i).left), Math.max(newInterval.right,
          intervals.get(i).right));
      ++i;
    }
    result.add(newInterval);

    // Inserts intervals appearing after newInterval.
    result.addAll(intervals.subList(i, intervals.size()));
    return result;
  }
  // @exclude

  private static void checkIntervals(List<Interval> result) {
    // Only check the intervals do not overlap with each other.
    for (int i = 1; i < result.size(); ++i) {
      assert (result.get(i - 1).right < result.get(i).left);
    }
  }

  private static void smallTest() {
    List<Interval> A = new ArrayList<Interval>() {
      {
        add(new Interval(1, 5));
      }
    };
    Interval newOne = new Interval(0, 3);
    List<Interval> result = insertInterval(A, newOne);
    assert (result.size() == 1 && result.get(0).left == 0 && result.get(0).right == 5);
    newOne = new Interval(0, 0);
    result = insertInterval(A, newOne);
    assert (result.size() == 2 && result.get(0).left == 0 && result.get(0).right == 0
        && result.get(1).left == 1 && result.get(1).right == 5);
  }

  public static void main(String[] args) {
    smallTest();
    Random r = new Random();
    for (int times = 0; times < 1000; ++times) {
      int n;
      if (args.length == 1) {
        n = Integer.parseInt(args[0]);
      } else {
        n = r.nextInt(10000) + 1;
      }
      List<Interval> A = new ArrayList<>();
      int pre = 0;
      for (int i = 0; i < n; ++i) {
        Interval temp = new Interval();
        temp.left = pre + r.nextInt(10) + 1;
        temp.right = temp.left + r.nextInt(10) + 1;
        pre = temp.right;
        A.add(temp);
      }
      Interval target = new Interval();
      target.left = r.nextInt(101);
      target.right = target.left + r.nextInt(101);
      List<Interval> result = insertInterval(A, target);
      checkIntervals(result);
    }
  }
}
