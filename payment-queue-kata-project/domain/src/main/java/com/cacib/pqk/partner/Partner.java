package com.cacib.pqk.partner;

import com.cacib.pqk.partner.application.ApplicationInfo;
import com.cacib.pqk.partner.type.Direction;
import com.cacib.pqk.partner.type.FlowType;
import com.cacib.pqk.partner.type.EnumValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Generated;

@Data
@Generated
public class Partner {
	@NotBlank(message = "Alias is required")
	@Size(max = 50, message = "Alias must be at most 50 characters")
	private String alias;

	@NotBlank(message = "Partner type is required")
	@Size(max = 30, message = "Partner type must be at most 30 characters")
	private String type;

	@EnumValidator(enumClass = Direction.class, message = "Direction must be INBOUND or OUTBOUND")
	private Direction direction;

	@EnumValidator(enumClass = FlowType.class, message = "Flow type must be MESSAGE, ALERTING or NOTIFICATION")
	private FlowType flowType;

	@NotBlank(message = "Description is required")
	private String description;

	@Valid
	private ApplicationInfo application;
}
