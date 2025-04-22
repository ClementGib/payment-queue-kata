package com.cacib.pqk.persistence.message;

import com.cacib.pqk.message.Message;
import com.cacib.pqk.persistence.partner.json.JsonConverter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = JsonConverter.class)
public interface MessageMapper {
    Message toDomain(MessageEntity entity);
    MessageEntity toEntity(Message domain);
}
