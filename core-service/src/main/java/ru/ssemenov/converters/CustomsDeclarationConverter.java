package ru.ssemenov.converters;

import ru.ssemenov.dtos.CustomsDeclarationDto;
import ru.ssemenov.entities.CustomsDeclaration;

/**
 * A converter to receive {@link ru.ssemenov.dtos.CustomsDeclarationDto} from {@link ru.ssemenov.entities.CustomsDeclaration} entity
 */
public class CustomsDeclarationConverter {
    public CustomsDeclarationDto entityToDto(CustomsDeclaration c) {
        return CustomsDeclarationDto.builder()
                .number(c.getNumber())
                .status(c.getStatus())
                .consignee(c.getConsignee())
                .vatCode(c.getVatCode())
                .invoiceData(c.getInvoiceData())
                .goodsValue(c.getGoodsValue())
                .dateOfSubmission(c.getDateOfSubmission())
                .dateOfRelease(c.getDateOfRelease())
                .build();
    }
}
