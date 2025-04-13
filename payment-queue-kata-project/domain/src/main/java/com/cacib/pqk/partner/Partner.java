package com.cacib.pqk.partner;

import com.cacib.pqk.partner.type.Direction;
import com.cacib.pqk.partner.type.FlowType;
import jakarta.validation.constraints.*;
import lombok.Generated;

@Generated
public class Partner {
	@NotBlank(message = "Alias is required")
	@Size(max = 50, message = "Alias must be at most 50 characters")
	private String alias;

	@NotBlank(message = "Partner type is required")
	@Size(max = 30, message = "Partner type must be at most 30 characters")
	private String type;

	@NotNull(message = "Direction is required")
	@Size(max = 1, message = "Direction must be at most 1 characters")
	private Direction direction;

	@NotNull(message = "Processed flow type is required")
	@Size(max = 1, message = "Processed flow type must be at most 1 characters")
	private FlowType flowType;

	@NotBlank(message = "Description is required")
	private String description;

	@Size(max = 100, message = "Application name must be at most 100 characters")
	private String application;
}
