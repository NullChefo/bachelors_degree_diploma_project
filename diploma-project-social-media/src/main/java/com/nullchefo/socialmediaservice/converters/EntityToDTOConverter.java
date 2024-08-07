package com.nullchefo.socialmediaservice.converters;

import com.nullchefo.socialmediaservice.DTO.EditUserDTO;
import com.nullchefo.socialmediaservice.DTO.UserRegisterDTO;
import com.nullchefo.socialmediaservice.DTO.create.PostCreateDTO;
import com.nullchefo.socialmediaservice.DTO.retrieve.*;
import com.nullchefo.socialmediaservice.entity.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class EntityToDTOConverter {

    private final String awsPath;

    public EntityToDTOConverter(@Value("${aws.s3.public-path}") final String awsPath) {
        this.awsPath = awsPath;
    }

    public LikeRetrieveDTO likeToLikeRetrieveDTO(Like likes, User user) {
        return LikeRetrieveDTO.builder().id(likes.getId()).user(convertUserToUserRetrieveDTO(user)).build();
    }

    public List<LikeRetrieveDTO> likesToLikesRetrieveDTO(List<Like> likes, User user) {
        List<LikeRetrieveDTO> likeRetrieveDTOs = new ArrayList<>();

        if (likes == null) {
            return new ArrayList<>();
        }

        for (Like like : likes) {
            likeRetrieveDTOs.add(likeToLikeRetrieveDTO(like, user));
        }
        return likeRetrieveDTOs;
    }

    public PostRetrieveDTO postToPostRetrievalDTO(Post post) {

        return PostRetrieveDTO
                .builder()
                .id(post.getId())
                .content(post.getContent())
                .creator(convertUserToUserRetrieveDTO(post.getCreator()))
                .createdAt(post.getCreatedAt())
                //	.likes(likesToLikesRetrieveDTO(post.getLikes(), post.getCreator()))
                //	.comments(commentsToCommentsRetrieveDTO(post.getComments()))
                .attachments(mediasToMediaRetrieveDTO(post.getAttachments()))
                .likeCount(post.getLikeCount())
                .createdAt(post.getCreatedAt())
                .commentCount(post.getCommentCount())
                .build();
    }

    public Post postCreateDTOToPost(PostCreateDTO postCreateDTO, User user, List<Media> media) {
        return Post
                .builder()
                .content(postCreateDTO.getContent())
                .creator(user)
                .attachments(media)
                .postUUID(postCreateDTO.getPostUUID())
                .build();
    }

    public List<PostRetrieveDTO> postsToPostsRetrievalDTO(List<Post> posts, final User currentUser) {
        List<PostRetrieveDTO> postRetrieveDTOs = new ArrayList<>();
        if (posts == null) {
            return new ArrayList<>();
        }

        for (Post post : posts) {
            PostRetrieveDTO postRetrieveDTO = postToPostRetrievalDTO(post);

            if (!post.getLikes().isEmpty()) {
                post.getLikes().parallelStream().forEach(like -> {
                    if (like.getCreator() != null) {
                        if (like.getCreator().getId().equals(currentUser.getId())) {
                            postRetrieveDTO.setLiked(true);
                        }
                    }
                });
            }
            if (!post.getComments().isEmpty()) {
                post.getComments().parallelStream().forEach(comment -> {
                    if (comment.getCreator() != null) {
                        if (comment.getCreator().getId().equals(currentUser.getId())) {
                            postRetrieveDTO.setCommented(true);
                        }
                    }

                });

            }

            postRetrieveDTOs.add(postRetrieveDTO);
        }
        return postRetrieveDTOs;
    }

    public UserRetrieveDTO convertUserToUserRetrieveDTO(User user) {
        return UserRetrieveDTO
                .builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .avatarURL(user.getAvatarURL())
                .verified(user.isVerified())
                //				.pronouns(user.getPronouns())
                .build();
    }

    private List<UserRetrieveDTO> usersToUserRetrieveDTO(List<User> users) {

        List<UserRetrieveDTO> userRetrieveDTOs = new ArrayList<>();

        if (users == null) {
            return new ArrayList<>();
        }

        for (User user : users) {
            userRetrieveDTOs.add(convertUserToUserRetrieveDTO(user));
        }
        return userRetrieveDTOs;
    }

    public CommentRetrieveDTO commentToCommentRetrieveDTO(Comment comment) {
        return CommentRetrieveDTO
                .builder()
                .id(comment.getId())
                .content(comment.getContent())
                .creator(convertUserToUserRetrieveDTO(comment.getCreator()))
                .timestamp(comment.getCreatedAt())
                //	.likes(likesToLikesRetrieveDTO(comment.getLikes(), comment.getCreator()))
                .likeCount(comment.getLikeCount())
                .build();
    }

    public List<CommentRetrieveDTO> commentsToCommentsRetrieveDTO(List<Comment> comments, final User currentUser) {

        List<CommentRetrieveDTO> commentRetrieveDTOs = new ArrayList<>();

        if (comments == null) {
            return new ArrayList<>();
        }

        for (Comment comment : comments) {
            CommentRetrieveDTO commentRetrieveDTO = commentToCommentRetrieveDTO(comment);

            if (!comment.getLikes().isEmpty()) {
                comment.getLikes().parallelStream().forEach(like -> {
                    if (like.getCreator() != null) {
                        if (like.getCreator().getId().equals(currentUser.getId())) {
                            commentRetrieveDTO.setLiked(true);
                        }
                    }
                });
            }
            commentRetrieveDTOs.add(commentRetrieveDTO);
        }
        return commentRetrieveDTOs;
    }

    public MediaRetrieveDTO mediaToMediaRetrieveDTO(Media media) {

        String folderPath = media.getPath().split("/")[1];

        if (folderPath.equals("")) {
            throw new NotFoundException("Media path is empty");
        }

        String finalPath = awsPath + "/" + folderPath + "/" + media.getFileName();

        return MediaRetrieveDTO.builder().id(media.getId()).path(finalPath).creator(media.getCreatorId()).build();
    }

    public List<MediaRetrieveDTO> mediasToMediaRetrieveDTO(List<Media> medias) {
        List<MediaRetrieveDTO> mediaRetrieveDTOs = new ArrayList<>();
        if (medias == null) {
            return new ArrayList<>();
        }
        for (Media medias1 : medias) {
            mediaRetrieveDTOs.add(mediaToMediaRetrieveDTO(medias1));
        }
        return mediaRetrieveDTOs;
    }

    public ConnectionRetrievalDTO friendToFriendRetrievalDTO(Connection connection) {
        if (connection == null) {
            return null;
        }
        return ConnectionRetrievalDTO
                .builder()
                .id(connection.getId())
                .connection(convertUserToUserRetrieveDTO(connection.getConnection()))
                .accepted(connection.isAccepted())
                .build();
    }

    public List<ConnectionRetrievalDTO> friendsToFriendsRetrievalDTO(List<Connection> connections) {
        List<ConnectionRetrievalDTO> friendRetrieveDTOs = new ArrayList<>();
        if (connections == null) {
            return new ArrayList<>();
        }
        for (Connection connection : connections) {
            friendRetrieveDTOs.add(friendToFriendRetrievalDTO(connection));
        }
        return friendRetrieveDTOs;
    }

    public List<MessageRetrieveDTO> messagesToMessagesRetrievalDTO(final List<Message> content) {
        List<MessageRetrieveDTO> messageRetrieveDTOs = new ArrayList<>();
        if (content == null) {
            return new ArrayList<>();
        }
        for (Message message : content) {
            messageRetrieveDTOs.add(messageToMessagesRetrievalDTO(message));
        }
        return messageRetrieveDTOs;
    }

    public MessageRetrieveDTO messageToMessagesRetrievalDTO(final Message content) {
        return MessageRetrieveDTO
                .builder()
                .id(content.getId())
                .content(content.getContent())
                .recipient(convertUserToUserRetrieveDTO(content.getSender()))
                .creationDate(content.getCreatedAt())
                .build();
    }

    public GroupRetrieveDTO groupToGroupRetrieveDTO(Group group) {

        return GroupRetrieveDTO
                .builder()
                .id(group.getId())
                .name(group.getName())
                .description(group.getDescription())
                .owner(convertUserToUserRetrieveDTO(group.getOwner()))
                .members(usersToUserRetrieveDTO(group.getMembers()))
                .attachments(mediasToMediaRetrieveDTO(group.getAttachments()))
                .avatarURL(group.getAvatarURL())
                .memberCount(group.getMemberCount())
                .build();
    }

    public List<GroupRetrieveDTO> groupsToGroupsRetrieveDTO(List<Group> groups) {
        List<GroupRetrieveDTO> groupRetrieveDTOs = new ArrayList<>();
        if (groups == null) {
            return new ArrayList<>();
        }
        for (Group group : groups) {
            groupRetrieveDTOs.add(groupToGroupRetrieveDTO(group));
        }
        return groupRetrieveDTOs;
    }

    public Post postRetrieveDTOUpdatePost(final Post existingPost, final PostRetrieveDTO post) {
        existingPost.setContent(post.getContent());
        existingPost.setUpdatedAt(LocalDateTime.now());
        return existingPost;
    }

    public List<ConnectionRetrievalDTO> connectionsToConnectionRetrievalDTO(final List<Connection> content) {

        List<ConnectionRetrievalDTO> connectionRetrievalDTOs = new ArrayList<>();
        if (content == null) {
            return new ArrayList<>();
        }
        for (Connection connection : content) {
            connectionRetrievalDTOs.add(connectionToConnectionRetrievalDTO(connection));
        }
        return connectionRetrievalDTOs;
    }

    public ConnectionRetrievalDTO connectionToConnectionRetrievalDTO(final Connection content) {
        return ConnectionRetrievalDTO
                .builder()
                .id(content.getId())
                .connectionDate(content.getConnectionDate())
                .connection(convertUserToUserRetrieveDTO(content.getConnection()))
                .accepted(content.isAccepted())
                .build();
    }

    //	public <T> Page<T> listToPage(final Pageable paging, final List<T> list) {
    //		//		final int start = (int) paging.getOffset();
    //		//		final int end = Math.min((start + paging.getPageSize()), list.size());
    //		return new PageImpl<>(list, paging, list.size());
    //	}

    public List<HighValueUserRetrieveDTO> highValueUserListToHighValueUserRetrieveDTOList(final List<HighValueUser> content) {

        List<HighValueUserRetrieveDTO> highValueUserRetrieveDTOs = new ArrayList<>();
        if (content == null) {
            return new ArrayList<>();
        }
        for (HighValueUser highValueUser : content) {
            highValueUserRetrieveDTOs.add(highValueUserToHighValueUserRetrieveDTO(highValueUser));
        }
        return highValueUserRetrieveDTOs;
    }

    public HighValueUserRetrieveDTO highValueUserToHighValueUserRetrieveDTO(final HighValueUser content) {
        return HighValueUserRetrieveDTO.builder().user(convertUserToUserRetrieveDTO(content.getUser())).build();
    }

    public EditUserDTO convertUserToEditUserRetrievalDTO(final User user) {
        return EditUserDTO
                .builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .avatarURL(user.getAvatarURL())
                .isOauth(user.isOauthUser())
                .email(user.getEmail())
                .pronouns(user.getPronouns())
                .work(user.getWork())
                .about(user.getAbout())
                .phone(user.getPhone())
                .websiteURL(user.getWebsiteURL())
                .linkedInURL(user.getLinkedInURL())
                .school(user.getSchool())
                .university(user.getUniversity())
                .build();
    }

    public ProfileRetrieveDTO convertuserToProfileRetrieveDTO(final User user) {
        return ProfileRetrieveDTO
                .builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .avatarURL(user.getAvatarURL())
                .isOauth(user.isOauthUser())
                .connectionsCount(user.getConnectionsCount())
                .attachments(mediasToMediaRetrieveDTO(user.getAttachments()))
                .pronouns(user.getPronouns())
                .work(user.getWork())
                .about(user.getAbout())
                .phone(user.getPhone())
                .websiteURL(user.getWebsiteURL())
                .linkedInURL(user.getLinkedInURL())
                .school(user.getSchool())
                .verified(user.isVerified())
                .verifiedAt(user.getVerifiedAt())
                .university(user.getUniversity())
                .build();
    }

    private List<UserRetrieveDTO> connectionsToUsersDTO(final List<Connection> connection) {
        List<UserRetrieveDTO> userRetrieveDTOs = new ArrayList<>();
        if (connection == null) {
            return new ArrayList<>();
        }
        for (Connection con : connection) {
            userRetrieveDTOs.add(convertUserToUserRetrieveDTO(con.getConnection()));
        }
        return userRetrieveDTOs;
    }

    public List<UserRetrieveDTO> usersToUsersRetrieveDTO(final List<User> users) {
        List<UserRetrieveDTO> userRetrieveDTOs = new ArrayList<>();
        if (users == null) {
            return new ArrayList<>();
        }
        for (User user : users) {
            userRetrieveDTOs.add(convertUserToUserRetrieveDTO(user));
        }
        return userRetrieveDTOs;
    }

    public User convertUserRegisterDTOTOUser(final UserRegisterDTO userRegisterDTO) {
        return User
                .builder()
                .username(userRegisterDTO.getUsername().toLowerCase())
                .firstName(userRegisterDTO.getFirstName())
                .lastName(userRegisterDTO.getLastName())
                .email(userRegisterDTO.getEmail().toLowerCase())
                .password(userRegisterDTO.getPassword())
                .createdAt(LocalDateTime.now())
                .pronouns(userRegisterDTO.getPronouns())
                .isOauthUser(false)
                .verified(false)
                .enabled(false)
                .accountNotLocked(true)
                .accountNotExpired(true)
                .grantedAuthorities("ROLE_USER")
                .build();
    }

    public List<LikeRetrieveDTO> convertLikesToLikesRetrieveDTO(final List<Like> content) {
        List<LikeRetrieveDTO> likeRetrieveDTOs = new ArrayList<>();
        if (content == null) {
            return new ArrayList<>();
        }
        for (Like like : content) {
            likeRetrieveDTOs.add(convertLikeToLikeRetrieveDTO(like));
        }
        return likeRetrieveDTOs;
    }

    private LikeRetrieveDTO convertLikeToLikeRetrieveDTO(final Like like) {
        return LikeRetrieveDTO
                .builder()
                .id(like.getId())
                .user(convertUserToUserRetrieveDTO(like.getCreator()))
                .build();
    }

}
