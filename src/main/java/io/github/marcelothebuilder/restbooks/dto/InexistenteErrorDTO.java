package io.github.marcelothebuilder.restbooks.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.github.marcelothebuilder.restbooks.util.json.JsonDateFormat;
import lombok.Data;

@Data
public class InexistenteErrorDTO {
	private String mensagem;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=JsonDateFormat.ISO_8601_24H_FULL_FORMAT, timezone="GMT-3")
	public Date getTimestamp() {
		return new Date();
	}
}
