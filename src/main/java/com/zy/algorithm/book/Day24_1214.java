package com.zy.algorithm.book;

import java.util.Arrays;

public class Day24_1214 {

    /**
     * @return 识别数组的所有元素乘积，正数为1，负数为-1，否则为0
     */
    public static int arraySign(int[] nums) {
        int result = 1;
        for (int i : nums) {
            if (i == 0) {
                return 0;
            } else if (i > 0) {
            } else {
                result = -result;
            }
        }
        return result;
    }

    /**
     * <a href="https://leetcode.cn/problems/find-the-winner-of-the-circular-game/description/">查找游戏获胜者</a>
     *
     * 共有 n 名小伙伴一起做游戏。小伙伴们围成一圈，按 顺时针顺序 从 1 到 n 编号。
     * 确切地说，从第 i 名小伙伴顺时针移动一位会到达第 (i+1) 名小伙伴的位置，其中 1 <= i < n ，从第 n 名小伙伴顺时针移动一位会回到第 1 名小伙伴的位置。
     *
     * 游戏遵循如下规则：
     * 从第 1 名小伙伴所在位置 开始 。
     * 沿着顺时针方向数 k 名小伙伴，计数时需要 包含 起始时的那位小伙伴。逐个绕圈进行计数，一些小伙伴可能会被数过不止一次。
     * 你数到的最后一名小伙伴需要离开圈子，并视作输掉游戏。
     * 如果圈子中仍然有不止一名小伙伴，从刚刚输掉的小伙伴的 顺时针下一位 小伙伴 开始，回到步骤 2 继续执行。
     * 否则，圈子中最后一名小伙伴赢得游戏。
     * 给你参与游戏的小伙伴总数 n ，和一个整数 k ，返回游戏的获胜者。
     *
     * 暴力解法：按照链表进行查找
     * 优化：提前计算出需要被淘汰的小伙伴，时间复杂度 O(n)
     */

    public static int findWinner2(int n, int k) {
        if (n <= 1) {
            return 1;
        }

        if (n <= 1) {
            return 1;
        }

        // 每个值代表指向下位小伙伴下标
        int[] nums = new int[n];
        for (int i = 0; i < n; ++i) {
            nums[i] = i + 1;
        }
        // 循环
        nums[n - 1] = 0;

        // 每轮开始的下标
        int firstIndex = 0;
        int count = n;
        int previousIndex = firstIndex;

        // 计算被淘汰的下标
        while (true) {
            int loserCount = (k - 1) % count;
            for (int i = 0; i < loserCount; ++i) {
                int nextIndex = nums[firstIndex];
                previousIndex = firstIndex;
                firstIndex = nextIndex;
            }
            // 从输家的下一个坐标开始游戏
            firstIndex = nums[firstIndex];
            // 输家的上一个坐标指向下下个坐标
            nums[previousIndex] = firstIndex;
            --count;

            if (count == 1) {
                return firstIndex + 1;
            }
        }
    }

    public static int findWinner1(int n, int k) {
        if (n <= 1) {
            return 1;
        }

        Partner first = new Partner(1);
        Partner previous = first;
        for (int i = 1; i < n; ++i) {
            previous.next = new Partner(i + 1);
            previous = previous.next;
            previous.next = first;
        }

        while (true) {
            for (int i = 1; i < k; ++i) {
                previous = first;
                first = first.next;
            }
            previous.next = first.next;
            if (previous.next == previous) {
                return previous.value;
            }
            first = previous.next;
        }
    }

    static class Partner {
        Partner next;
        int value;

        Partner(int v) {
            value = v;
        }
    }

    /**
     * <a href="https://leetcode.cn/problems/minimum-sideway-jumps/description/">最少侧跳次数</a>
     * 给你一个长度为 n 的 3 跑道道路 ，它总共包含 n + 1 个 点 ，编号为 0 到 n 。一只青蛙从 0 号点第二条跑道 出发 ，它想要跳到点 n 处。然而道路上可能有一些障碍。
     * 给你一个长度为 n + 1 的数组 obstacles ，其中 obstacles[i] （取值范围从 0 到 3）表示在点 i 处的 obstacles[i] 跑道上有一个障碍。如果 obstacles[i] == 0 ，那么点 i 处没有障碍。任何一个点的三条跑道中 最多有一个 障碍。
     * 比方说，如果 obstacles[2] == 1 ，那么说明在点 2 处跑道 1 有障碍。
     * 这只青蛙从点 i 跳到点 i + 1 且跑道不变的前提是点 i + 1 的同一跑道上没有障碍。为了躲避障碍，这只青蛙也可以在 同一个 点处 侧跳 到 另外一条 跑道（这两条跑道可以不相邻），但前提是跳过去的跑道该点处没有障碍。
     * 比方说，这只青蛙可以从点 3 处的跑道 3 跳到点 3 处的跑道 1 。
     * 这只青蛙从点 0 处跑道 2 出发，并想到达点 n 处的 任一跑道 ，请你返回 最少侧跳次数 。
     * 注意：点 0 处和点 n 处的任一跑道都不会有障碍。
     *
     * 思路：贪婪算法，每次取空闲距离最长的
     */
    public static int minJumpCount(int[] obstacles) {
        // 赛道
        int track = 2;
        int count = 0;
        int pos = 0;
        // 每条跑道的障碍是否存在
        boolean[] obstacleExist = new boolean[3];
        int nextTrack = track;
        while (pos < obstacles.length) {
            // 计算下一步的最优路径
            if (obstacles[pos] > 0) {
                nextTrack = obstacles[pos];
                obstacleExist[nextTrack - 1] = true;
            }
            // 找到最长的路径，然后记录位置
            if (obstacleExist[0] && obstacleExist[1] && obstacleExist[2]) {
                if (nextTrack != track) {
                    ++count;
                }
                pos--;
                track = nextTrack;
                obstacleExist[0] = false;
                obstacleExist[1] = false;
                obstacleExist[2] = false;
            } else {
                ++pos;
                if (pos == obstacles.length) {
                    if (obstacleExist[track - 1]) {
                        ++count;
                    }
                }
            }

        }
        return count;
    }


    /**
     * 官方题解
     */
    public static int minSideJumps(int[] obstacles) {
        int n = obstacles.length;
        int ans = 0;
        for (int i = 0, lane = 1; i < n - 1; i++) {
            int lanePlus = lane + 1;
            if (obstacles[i + 1] != lanePlus)
                continue;
            while (i < n - 1 && (obstacles[i] == lanePlus || obstacles[i] == 0))
                i++;
            int one = lanePlus % 3;
            lane = obstacles[i] == one + 1 ? (lane + 2) % 3 : one;
            i--;
            ans++;
        }
        return ans;
    }

}
