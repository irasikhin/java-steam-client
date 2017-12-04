package ru.ir.steam.client.cmd;

import lombok.Data;
import ru.ir.steam.dto.SendTradeOffer;

@Data
public class SendTradeOfferMessage {

    private final SendTradeOffer tradeOffer;

}
