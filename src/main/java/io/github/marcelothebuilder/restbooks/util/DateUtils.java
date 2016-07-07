package io.github.marcelothebuilder.restbooks.util;

import java.sql.Timestamp;
import java.util.Date;

public class DateUtils {
	public static Date toDate(Timestamp timestamp) {
		if (timestamp == null) {
			return null;
		}
		return new Date(timestamp.getTime());
	}

	public static Timestamp toTimestamp(Date date) {
		if (date == null) {
			return null;
		}
		return new Timestamp(date.getTime());
	}
}
