package com.epam.idea.task.nine.parser;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ActionType {
    @JsonProperty("unload")
    UNLOAD,
    @JsonProperty("load")
    LOAD,
    @JsonProperty("unload load")
    UNLOAD_LOAD
}
