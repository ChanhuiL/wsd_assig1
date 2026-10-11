package kr.ac.jbnu.lch.wsd202011711.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseDto<T> {
    private String status;
    private T data;
}
