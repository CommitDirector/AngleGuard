package me.elilroy.angleguard.utils;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONObject;

public class DiscordWebhook {
    private final String url;
    private String content;
    private String username;
    private String avatarUrl;
    private boolean tts;
    private final List<EmbedObject> embeds = new ArrayList();

    public DiscordWebhook(String url) {
        this.url = url;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setTts(boolean tts) {
        this.tts = tts;
    }

    public void addEmbed(EmbedObject embed) {
        this.embeds.add(embed);
    }

    public void execute() throws IOException {
        if (this.content == null && this.embeds.isEmpty()) {
            throw new IllegalArgumentException("Set content or add at least one EmbedObject");
        } else {
            JSONObject json = new JSONObject();
            json.put("content", this.content);
            json.put("username", this.username);
            json.put("avatar_url", this.avatarUrl);
            json.put("tts", this.tts);
            if (!this.embeds.isEmpty()) {
                List<JSONObject> embedObjects = new ArrayList();

                for(EmbedObject embed : this.embeds) {
                    JSONObject jsonEmbed = new JSONObject();
                    jsonEmbed.put("title", embed.getTitle());
                    jsonEmbed.put("description", embed.getDescription());
                    jsonEmbed.put("url", embed.getUrl());
                    if (embed.getColor() != null) {
                        Color color = embed.getColor();
                        int rgb = color.getRed() << 16 | color.getGreen() << 8 | color.getBlue();
                        jsonEmbed.put("color", rgb);
                    }

                    if (embed.getFooter() != null) {
                        JSONObject jsonFooter = new JSONObject();
                        jsonFooter.put("text", embed.getFooter().getText());
                        jsonFooter.put("icon_url", embed.getFooter().getIconUrl());
                        jsonEmbed.put("footer", jsonFooter);
                    }

                    if (embed.getThumbnail() != null) {
                        JSONObject jsonThumbnail = new JSONObject();
                        jsonThumbnail.put("url", embed.getThumbnail().getUrl());
                        jsonEmbed.put("thumbnail", jsonThumbnail);
                    }

                    List<JSONObject> jsonFields = new ArrayList();

                    for(EmbedObject.Field field : embed.getFields()) {
                        JSONObject jsonField = new JSONObject();
                        jsonField.put("name", field.getName());
                        jsonField.put("value", field.getValue());
                        jsonField.put("inline", field.isInline());
                        jsonFields.add(jsonField);
                    }

                    jsonEmbed.put("fields", jsonFields.toArray(new JSONObject[0]));
                    embedObjects.add(jsonEmbed);
                }

                json.put("embeds", embedObjects.toArray(new JSONObject[0]));
            }

            URL url = new URL(this.url);
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
            connection.addRequestProperty("Content-Type", "application/json");
            connection.addRequestProperty("User-Agent", "Java-DiscordWebhook");
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            OutputStream stream = connection.getOutputStream();
            stream.write(json.toString().getBytes());
            stream.flush();
            stream.close();
            connection.getInputStream().close();
            connection.disconnect();
        }
    }

    public static class EmbedObject {
        private String title;
        private String description;
        private String url;
        private Color color;
        private Footer footer;
        private Thumbnail thumbnail;
        private final List<Field> fields = new ArrayList();

        public EmbedObject() {
        }

        public String getTitle() {
            return this.title;
        }

        public EmbedObject setTitle(String title) {
            this.title = title;
            return this;
        }

        public String getDescription() {
            return this.description;
        }

        public EmbedObject setDescription(String description) {
            this.description = description;
            return this;
        }

        public String getUrl() {
            return this.url;
        }

        public EmbedObject setUrl(String url) {
            this.url = url;
            return this;
        }

        public Color getColor() {
            return this.color;
        }

        public EmbedObject setColor(Color color) {
            this.color = color;
            return this;
        }

        public Footer getFooter() {
            return this.footer;
        }

        public EmbedObject setFooter(String text, String iconUrl) {
            this.footer = new Footer(text, iconUrl);
            return this;
        }

        public Thumbnail getThumbnail() {
            return this.thumbnail;
        }

        public EmbedObject setThumbnail(String url) {
            this.thumbnail = new Thumbnail(url);
            return this;
        }

        public List<Field> getFields() {
            return this.fields;
        }

        public EmbedObject addField(String name, String value, boolean inline) {
            this.fields.add(new Field(name, value, inline));
            return this;
        }

        public static class Footer {
            private final String text;
            private final String iconUrl;

            public Footer(String text, String iconUrl) {
                this.text = text;
                this.iconUrl = iconUrl;
            }

            public String getText() {
                return this.text;
            }

            public String getIconUrl() {
                return this.iconUrl;
            }
        }

        public static class Thumbnail {
            private final String url;

            public Thumbnail(String url) {
                this.url = url;
            }

            public String getUrl() {
                return this.url;
            }
        }

        public static class Field {
            private final String name;
            private final String value;
            private final boolean inline;

            public Field(String name, String value, boolean inline) {
                this.name = name;
                this.value = value;
                this.inline = inline;
            }

            public String getName() {
                return this.name;
            }

            public String getValue() {
                return this.value;
            }

            public boolean isInline() {
                return this.inline;
            }
        }
    }
}
