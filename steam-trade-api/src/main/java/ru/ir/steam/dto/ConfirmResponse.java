package ru.ir.steam.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ConfirmResponse implements Serializable {

    private boolean success;

}
