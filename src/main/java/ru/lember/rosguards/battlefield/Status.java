package ru.lember.rosguards.battlefield;

import lombok.Getter;

public enum Status {

    FIGHTING("Уличные протесты в процессе, нужно больше автозаков!"),
    TRIUMPH("Улицы зачищены, все люди погружены в автозаки! Больше никто уже не сможет выйти на улицы! Можете быть собой гордиться и надеяться на повышение :)"),
    GAME_OVER("Толпу не сдержать! Вы не оправдали оказанного доверия, будьте готовы отдать несколько звезд :("),
	;

    @Getter
    private String value;

	Status(String value) {
		this.value = value;
	}
}
