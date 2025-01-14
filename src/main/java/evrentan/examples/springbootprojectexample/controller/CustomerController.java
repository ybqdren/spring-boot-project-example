package evrentan.examples.springbootprojectexample.controller;

import evrentan.examples.springbootprojectexample.annotation.ApiLogger;
import evrentan.examples.springbootprojectexample.dto.Customer;
import evrentan.examples.springbootprojectexample.dto.CustomerRef;
import evrentan.examples.springbootprojectexample.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/customer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Customer Related APIs")
@ResponseBody
public class CustomerController {

  private final ICustomerService customerService;

  public CustomerController(ICustomerService customerService) {
    this.customerService = customerService;
  }

  @PostMapping
  @Operation(summary = "Create a Customer")
  @ApiResponses(value = {
      @ApiResponse(responseCode  = "200", description  = "Successfully Customer Created"),
      @ApiResponse(responseCode  = "404", description  = "Not Found"),
      @ApiResponse(responseCode  = "500", description  = "Internal Server Error")
  })
  @ApiLogger
  ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
    return ResponseEntity.ok(this.customerService.createCustomer(customer));
  }

  @GetMapping
  @Operation(summary = "Get All Customer Instances Reference IDs")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully Get All Customer Reference IDs"),
      @ApiResponse(responseCode = "404", description = "No Customer Reference IDs Found"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error")
  })
  ResponseEntity<List<CustomerRef>> getAllCustomerRefs() {
    final List<CustomerRef> customerRefList = this.customerService.getAllCustomerRefs();

    if (CollectionUtils.isNotEmpty(customerRefList))
      return ResponseEntity.ok(customerRefList);

    return ResponseEntity.notFound().build();
  }

  @GetMapping(value = "/{id}")
  @Operation(summary = "Get a Specific Customer Instance by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully Get Customer By ID"),
      @ApiResponse(responseCode = "404", description = "No Customer Found With ID"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error")
  })
  ResponseEntity<Customer> getCustomerById(@NotNull @PathVariable final String id) {
    final Customer customer = this.customerService.getCustomerById(id);

    if (Objects.nonNull(customer))
      return ResponseEntity.ok(customer);

    return ResponseEntity.notFound().build();
  }

  @PatchMapping(value = "/{id}")
  @Operation(summary = "Patch a Specific Customer Instance by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully Patch the Customer By ID"),
      @ApiResponse(responseCode = "400", description = "Bad Request for Patching the Customer"),
      @ApiResponse(responseCode = "404", description = "No Customer Found With ID"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error")
  })
  ResponseEntity<Customer> patchCustomer(@Validated @NotNull @PathVariable final String id, @RequestBody Customer customer) {
    return this.customerService.updateCustomer(id, customer);
  }

  @PutMapping(value = "/{id}")
  @Operation(summary = "Put a Specific Customer Instance by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully Put the Customer By ID"),
      @ApiResponse(responseCode = "400", description = "Bad Request for Putting the Customer"),
      @ApiResponse(responseCode = "404", description = "No Customer Found With ID"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error")
  })
  ResponseEntity<Customer> putCustomer(@Validated @NotNull @PathVariable final String id, @RequestBody Customer customer) {
    return this.customerService.updateCustomer(id, customer);
  }

  @DeleteMapping(value = "/{id}")
  @Operation(summary = "Delete a Specific Customer Instance by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully Put the Customer By ID"),
      @ApiResponse(responseCode = "400", description = "Bad Request for Putting the Customer"),
      @ApiResponse(responseCode = "404", description = "No Customer Found With ID"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error")
  })
  ResponseEntity<Customer> deleteCustomer(@PathVariable final String id) {
    return this.customerService.deleteCustomer(id);
  }
}
