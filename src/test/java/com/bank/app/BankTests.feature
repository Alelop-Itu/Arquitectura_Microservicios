Feature: Pruebas de API Bancaria

  Background:
    * url 'http://localhost:8080/api/v1'

  Scenario: Verificar saldo de Marianela Montalvo tras depósito
    Given path 'movements/filter'
    And param accountNumber = '225487'
    And param startDate = '2024-01-01T00:00:00'
    And param endDate = '2026-12-31T23:59:59'
    When method get
    Then status 200
    And match response[0].balance == 700.00