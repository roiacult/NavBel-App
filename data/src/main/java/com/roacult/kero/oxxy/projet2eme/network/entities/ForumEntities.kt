package com.roacult.kero.oxxy.projet2eme.network.entities

data class PostModel (
    val id : Long ,
    val postimg : String? ,
    val description : String ,
    val userid : Long ,
    val username : String ,
    val useryear : Int ,
    val userpicture : String
)
data class PostId(val postid :Long)
data class CommentModel(val id:Long , val userid:Long,val content: String , val useryear:Int , val username:String , val userpicture:String)
class GetCommentsResponse( reponse: Int , val data:List<CommentModel>):RemoteEntity(reponse)
data class PostCommentModel(val postid: Long , val content :String , val userid: Long)
data class CreatePostModel(val postimg: String? , val description: String ,val userid: Long)
class CreatePostResponse(reponse: Int):RemoteEntity(reponse)
class AllPostsResponse(reponse:Int ,val data:List<PostModel> ):RemoteEntity(reponse)
