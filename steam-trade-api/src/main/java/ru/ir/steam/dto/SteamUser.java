package ru.ir.steam.dto;

import lombok.Data;

@Data
public class SteamUser {

    private Long id;

    private String username;

    private String cookies;

    private String status;

}
