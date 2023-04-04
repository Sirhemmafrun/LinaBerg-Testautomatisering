Feature: Registration

  Scenario Outline: Register User
    Given I have chosen a "<browser>"
    And I have entered Email "<Email>"
    And I have entered Username "<Username>"
    And I have entered Password "<Password>"
    When I click on Sign Up
    Then I "<create>" an account


    Examples:
      | browser | Email            | Username | Password | create       |
      | chrome  | hejsan@hello.com | LinaHej  | Hejsan2@ | Yes          |
      | edge    | hejsan@hello.com | LinaHej  | Hejsan2@ | Yes          |
      | chrome  | hejsan@hello.com | Long     | Hejsan2@ | UserTooLong  |
      | edge    | hejsan@hello.com | Long     | Hejsan2@ | UserTooLong  |
      | chrome  | hejsan@hello.com | Lina22   | Hejsan2@ | UserExists   |
      | edge    | hejsan@hello.com | Lina22   | Hejsan2@ | UserExists   |
      | chrome  | Nothing          | Lina223  | Hejsan2@ | MissingEmail |
      | edge    | Nothing          | Lina223  | Hejsan2@ | MissingEmail |





    # 1 och 2 är godkända och fångar recaptcha eller något på nästa sida.
    # 3 och 4 användarnamn är mer än 100 tecken.
    # 5 och 6 är användarnamn som redan finns
    # 7 och 8 är email saknas


