package com.vet.hc.api.appointment.core.adapter.in.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.Path;
import lombok.NoArgsConstructor;

/**
 * Appointment controller.
 */
@Tag(name = "Appointment", description = "Veterinary appointment")
@Path("/appointment")
@NoArgsConstructor
public class AppointmentController {
    // private CreateAppointmentPort createAppointmentPort;
    // private FindAppointmentPort loadAppointmentPort;
    // private UpdateAppointmentPort updateAppointmentPort;
    // private DeleteAppointmentPort deleteAppointmentPort;
    // private Validator validator;

    // @Inject
    // public AppointmentController(
    // CreateAppointmentPort createAppointmentPort,
    // FindAppointmentPort loadAppointmentPort,
    // UpdateAppointmentPort updateAppointmentPort,
    // DeleteAppointmentPort deleteAppointmentPort,
    // Validator validator) {
    // this.createAppointmentPort = createAppointmentPort;
    // this.loadAppointmentPort = loadAppointmentPort;
    // this.updateAppointmentPort = updateAppointmentPort;
    // this.deleteAppointmentPort = deleteAppointmentPort;
    // this.validator = validator;
    // }

    // /**
    // * Get all appointments.
    // *
    // * @param page Page number.
    // * @param size Page size.
    // * @return The appointments paginated
    // */
    // @Operation(summary = "Get all appointments", description = "Get all
    // appointments using pages.", responses = {
    // @ApiResponse(responseCode = "200", description = "Appointments retrieved
    // successfully.", content = @Content(schema = @Schema(implementation =
    // PaginatedAppointmentResponse.class))),
    // @ApiResponse(responseCode = "400", description = "The page and size are empty
    // or size exceeded the limit.", content = @Content(schema =
    // @Schema(implementation = DetailedFailureResponse.class))),
    // })
    // @GET
    // @Path("/")
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response getAppointments(
    // @QueryParam("page") @Parameter(required = true, description = "Page number")
    // Integer page,
    // @QueryParam("size") @Parameter(required = true, description = "Page size (max
    // 10 elements)") Integer size,
    // @QueryParam("order_by") @Parameter(description = "Field to order by. The
    // field must be in snake case") String orderBy,
    // @QueryParam("order") @Parameter(description = "Order type, if it is empty, it
    // will be 'none'") String orderTypeStr,
    // @QueryParam("name") @Parameter(description = "Appointment name") String name)
    // {
    // OrderType orderType = null;
    // if (orderTypeStr != null)
    // try {
    // orderType = OrderType.valueOf(orderTypeStr.toUpperCase());
    // } catch (Exception e) {
    // return Response.status(Response.Status.BAD_REQUEST)
    // .entity(FailureResponse.builder()
    // .message("El tipo de orden no es válido, los valores permitidos son: "
    // + String.join(", ", EnumUtils.getEnumNames(OrderType.class)))
    // .build())
    // .build();
    // }

    // if (page == null || size == null) {
    // return Response.status(Response.Status.BAD_REQUEST)
    // .entity(FailureResponse.builder()
    // .message("La página y el tamaño son obligatorios")
    // .build())
    // .build();
    // }

    // else if (size > 10) {
    // return Response.status(Response.Status.BAD_REQUEST)
    // .entity(FailureResponse.builder()
    // .message("El tamaño máximo es 10")
    // .build())
    // .build();
    // }

    // Criteria criteria = new Criteria(
    // List.of(new Filter("name", FilterOperator.CONTAINS, name)),
    // Order.of(orderBy, orderType),
    // size,
    // page);

    // var result = loadAppointmentPort.match(criteria);

    // if (result.isFailure()) {
    // return Response.status(Response.Status.BAD_REQUEST)
    // .entity(FailureResponse.builder()
    // .message(result.getFailure().getMessage())
    // .build())
    // .build();
    // }

    // return Response.ok(result.getSuccess()).build();
    // }

    // /**
    // * Get appointment by id.
    // */
    // @Operation(summary = "Get appointment by id", description = "Get appointment
    // by id.", responses = {
    // @ApiResponse(responseCode = "200", description = "Appointment retrieved
    // successfully.", content = @Content(schema = @Schema(implementation =
    // AppointmentResponse.class))),
    // @ApiResponse(responseCode = "404", description = "The appointment was not
    // found.", content = @Content(schema = @Schema(implementation =
    // FailureResponse.class))),
    // })
    // @GET
    // @Path("/{id}")
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response getAppointmentById(@PathParam("id") Long id) {
    // Result<AppointmentDto, AppointmentFailure> result =
    // loadAppointmentPort.findById(id);

    // if (result.isFailure()) {
    // return Response.status(Response.Status.NOT_FOUND)
    // .entity(FailureResponse.builder()
    // .message(result.getFailure().getMessage())
    // .build())
    // .build();
    // }

    // return Response.ok(
    // AppointmentResponse.builder()
    // .message("Appointmento encontrado exitosamente")
    // .content(result.getSuccess())
    // .build())
    // .build();
    // }

    // /**
    // * Create a new appointment.
    // *
    // * @param request The appointment data.
    // * @return The created appointment
    // */
    // @Operation(summary = "Create a new appointment", description = "Create a new
    // appointment.", responses = {
    // @ApiResponse(responseCode = "200", description = "The appointment was created
    // successfully.", content = @Content(schema = @Schema(implementation =
    // AppointmentResponse.class))),
    // @ApiResponse(responseCode = "400", description = "Invalid appointment data.",
    // content = @Content(schema = @Schema(implementation =
    // DetailedFailureResponse.class))),
    // })
    // @POST
    // @Path("/")
    // @Consumes(MediaType.APPLICATION_JSON)
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response createAppointment(CreateAppointmentDto request) {
    // var violations = validator.validate(request);

    // if (!violations.isEmpty()) {
    // return Response.status(Response.Status.BAD_REQUEST)
    // .entity(FailureResponse.builder()
    // .message(violations.iterator().next().getMessage())
    // .build())
    // .build();
    // }

    // Result<AppointmentDto, AppointmentFailure> result =
    // createAppointmentPort.create(request);

    // if (result.isFailure()) {
    // return Response.status(Response.Status.BAD_REQUEST)
    // .entity(FailureResponse.builder()
    // .message(result.getFailure().getMessage())
    // .build())
    // .build();
    // }

    // return Response.ok(
    // AppointmentResponse.builder()
    // .message("Appointmento creado exitosamente")
    // .content(result.getSuccess())
    // .build())
    // .build();
    // }

    // /**
    // * Update a appointment.
    // *
    // * @param id The appointment id.
    // * @return The updated appointment
    // */
    // @Operation(summary = "Update a appointment", description = "Update a
    // appointment.", responses = {
    // @ApiResponse(responseCode = "200", description = "The appointment was updated
    // successfully.", content = @Content(schema = @Schema(implementation =
    // AppointmentResponse.class))),
    // @ApiResponse(responseCode = "400", description = "Invalid appointment data.",
    // content = @Content(schema = @Schema(implementation =
    // DetailedFailureResponse.class))),
    // @ApiResponse(responseCode = "404", description = "The appointment was not
    // found.", content = @Content(schema = @Schema(implementation =
    // FailureResponse.class))),
    // })
    // @PUT
    // @Path("/{id}")
    // @Consumes(MediaType.APPLICATION_JSON)
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response updateAppointment(@PathParam("id") Long id,
    // UpdateAppointmentDto request) {
    // if (!id.equals(request.getId())) {
    // return Response.status(Response.Status.BAD_REQUEST)
    // .entity(FailureResponse.builder()
    // .message("El id del appointmento no coincide con el id de la petición")
    // .build())
    // .build();
    // }

    // var violations = validator.validate(request);

    // if (!violations.isEmpty()) {
    // return Response.status(Response.Status.BAD_REQUEST)
    // .entity(FailureResponse.builder()
    // .message(violations.iterator().next().getMessage())
    // .build())
    // .build();
    // }

    // Result<AppointmentDto, AppointmentFailure> result =
    // updateAppointmentPort.update(request);

    // if (result.isFailure()) {
    // return Response.status(Response.Status.NOT_FOUND)
    // .entity(FailureResponse.builder()
    // .message(result.getFailure().getMessage())
    // .build())
    // .build();
    // }

    // return Response.ok(
    // AppointmentResponse.builder()
    // .message("Appointmento actualizado exitosamente")
    // .content(result.getSuccess())
    // .build())
    // .build();
    // }

    // /**
    // * Delete a appointment.
    // *
    // * @param id The appointment id.
    // * @return The deleted appointment
    // */
    // @Operation(summary = "Delete a appointment", description = "Delete a
    // appointment.", responses = {
    // @ApiResponse(responseCode = "200", description = "The appointment was deleted
    // successfully", content = @Content(schema = @Schema(implementation =
    // BasicResponse.class))),
    // @ApiResponse(responseCode = "404", description = "The appointment was not
    // found.", content = @Content(schema = @Schema(implementation =
    // FailureResponse.class))),
    // })
    // @DELETE
    // @Path("/{id}")
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response deleteAppointment(@PathParam("id") Long id) {
    // Result<Void, AppointmentFailure> result =
    // deleteAppointmentPort.deleteById(id);

    // if (result.isFailure()) {
    // return Response.status(Response.Status.NOT_FOUND)
    // .entity(FailureResponse.builder()
    // .message("El appointmento no fue encontrado")
    // .build())
    // .build();
    // }

    // return Response.ok(
    // BasicResponse.builder()
    // .message("El appointmento fue eliminado exitosamente")
    // .build())
    // .build();
    // }
}