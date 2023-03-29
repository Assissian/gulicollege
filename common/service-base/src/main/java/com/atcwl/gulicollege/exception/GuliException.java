package com.atcwl.gulicollege.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@Data
@NoArgsConstructor
public class GuliException extends RuntimeException {
    private Integer code;

    public GuliException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
