package com.cacib.pqk.persistence.partner;
import com.cacib.pqk.persistence.partner.direction.DirectionConverter;
import com.cacib.pqk.persistence.partner.flow.FlowTypeConverter;
import com.cacib.pqk.partner.type.Direction;
import com.cacib.pqk.partner.type.FlowType;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Type;

@Entity
@Table(schema = "pqkapp", name = "partners")
@Getter
@Setter
public class PartnerEntity {

    @Id
    @Column(name = "alias", nullable = false, unique = true, length = 50)
    private String alias;

    @Column(name = "type", nullable = false, length = 30)
    private String type;

    @Column(name = "direction", nullable = false, length = 1)
    @Convert(converter = DirectionConverter.class)
    private Direction direction;

    @Column(name = "flow_type", nullable = false, length = 1)
    @Convert(converter = FlowTypeConverter.class)
    private FlowType flowType;

    @Column(name = "description", nullable = false)
    private String description;

    @Type(JsonType.class)
    @Column(name = "application", columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    private String application;
}