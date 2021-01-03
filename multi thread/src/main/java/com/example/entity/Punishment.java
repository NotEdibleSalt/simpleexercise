package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: xush
 * @Date: 2020-12-29
 * @Version: v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Punishment {

    // 抄写的单词
    private String wordToCopy;

    // 剩余的抄写次数
    private int leftCopyCount;

}