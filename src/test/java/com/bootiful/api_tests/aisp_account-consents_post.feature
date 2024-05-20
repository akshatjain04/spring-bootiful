# ********RoostGPT********

# Test generated by RoostGPT for test MiniProjects using AI Type Open AI and AI Model gpt-4-1106-preview
# 
# Feature file generated for /aisp/account-consents_post for http method type POST 
# RoostTestHash=3a9edd08a1
# 
# 

# ********RoostGPT********
Feature: Account Consent Setup

  Background:
    * def urlBase = karate.properties['url.base'] || karate.get('urlBase', 'http://localhost:8080')
    * url urlBase
    * def authToken = karate.properties['AUTH_TOKEN']

  Scenario Outline: Create account consent with valid input payload
    Given path '/aisp/account-consents'
    And header Authorization = 'Bearer ' + authToken
    And header Content-Type = 'application/json'
    And header x-fapi-auth-date = <x-fapi-auth-date>
    And header x-fapi-customer-ip-address = <x-fapi-customer-ip-address>
    And header x-fapi-interaction-id = <x-fapi-interaction-id>
    And header Accept-Language = <Accept-Language>
    And request <requestBody>
    When method POST
    Then status 201
    And match responseHeaders['x-fapi-interaction-id'] == <x-fapi-interaction-id>
    And match response.data == <expectedResponseData>
    And match response.links.self == '#regex[http(s)?://.+]'

    Examples:
      | read('aisp_account-consents_post.csv') |

  Scenario: Create account consent with missing required headers
    Given path '/aisp/account-consents'
    And header Authorization = 'Bearer ' + authToken
    And header Content-Type = 'application/json'
    And request
      """
      { 
        "data": { 
          "permissions": ["ReadAccountBalance"], 
          "expirationDate": "2017-04-05T00:07:00Z" 
        } 
      }
      """
    When method POST
    Then status 400
    And match responseHeaders['Content-Type'] == 'application/json'
    And match response.errors[*].code contains 'OB.Header.Missing'

  Scenario: Access account consent with invalid authorization token
    Given path '/aisp/account-consents'
    And header Authorization = 'InvalidToken'
    And header Content-Type = 'application/json'
    And header x-fapi-auth-date = 'Sun, 10 Sep 2017 19:43:31 UTC'
    And header x-fapi-customer-ip-address = '169.254.169.254'
    And header x-fapi-interaction-id = 'unique-id-12345'
    And header Accept-Language = 'en-HK'
    And request
      """
      { 
        "data": { 
          "permissions": ["ReadAccountBalance"], 
          "expirationDate": "2017-04-05T00:07:00Z" 
        } 
      }
      """
    When method POST
    Then status 401
    And match responseHeaders['Content-Type'] == 'application/json'
    And match response == {}

  Scenario: Create account consent with unsupported media type
    Given path '/aisp/account-consents'
    And header Authorization = 'Bearer ' + authToken
    And header Content-Type = 'text/plain'
    And request 'Invalid Content-Type'
    When method POST
    Then status 415
    And match responseHeaders['Content-Type'] == 'application/json'
    And match response.errors[*].code contains 'OB.Header.Invalid'

  Scenario: Attempt to use unsupported HTTP method (GET)
    Given path '/aisp/account-consents'
    And header Authorization = 'Bearer ' + authToken
    And header Content-Type = 'application/json'
    When method GET
    Then status 405
    And match responseHeaders['Content-Type'] == 'application/json'
    And match response.errors[*].code contains 'OB.Field.Unexpected'
