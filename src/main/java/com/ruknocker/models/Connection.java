package com.ruknocker.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Connection {
    private String ip;
    private String login;
    private String port;

    private List<ConnectionPort> ports = new ArrayList<>();
}
