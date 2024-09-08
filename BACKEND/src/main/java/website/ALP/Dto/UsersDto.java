        package website.ALP.Dto;

        public class UsersDto {

            private String email;
            private String firstName;
            private String lastName;
            private String password;
            private String username;
            private int points;

            public UsersDto(){}
            public UsersDto(String username, String firstName, String lastName, String email, String password, int points) {
                this.email = email;
                this.firstName = firstName;
                this.password = password;
                this.username = username;
                this.points = points;
                this.lastName = lastName;
            }
            public String getEmail() {
                return email;
            }
            public void setEmail(String email) {
                this.email = email;
            }
            public String getFirstName() {
                return firstName;
            }
            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }
            public String getLastName() {
                return lastName;
            }
            public void setLastName(String lastName) {
                this.lastName = lastName;
            }
            public String getPassword() {
                return password;
            }
            public void setPassword(String password) {
                this.password = password;
            }
            public String getUsername() {
                return username;
            }
            public void setUsername(String username) {
                this.username = username;
            }
            public int getPoints() {
                return points;
            }
            public void setPoints(int points) {
                this.points = points;
            } 


        }
