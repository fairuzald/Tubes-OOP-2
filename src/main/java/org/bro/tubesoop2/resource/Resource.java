    package org.bro.tubesoop2.resource;

    public class Resource {
        private String name;

        public Resource(String name){
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        // first letter uppercase and the rest lowercase
        // if space the letter after it uppercase
        // if _, convert to space
        public String getFormattedName(){
            String[] words = name.split(" ");
            StringBuilder formattedName = new StringBuilder();
            for (String word : words) {
                formattedName.append(word.substring(0, 1).toUpperCase());
                formattedName.append(word.substring(1).toLowerCase());
                formattedName.append(" ");
            }
            return formattedName.toString().trim();
        }
    }
