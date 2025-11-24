package com.triquang.payload;

import lombok.Data;

@Data
public class Credential {
	private String type;
	private boolean temporary;
	private String value;
}
