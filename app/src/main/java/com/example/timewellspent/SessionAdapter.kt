package com.example.timewellspent

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault

class SessionAdapter(var sessionList: List<Session>) : RecyclerView.Adapter<SessionAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView
        val textViewDate: TextView
        val textViewHeartrate: TextView
        val textViewTimeSpent: TextView
        val textViewEmotion: TextView
        val layout: ConstraintLayout

        init {
            textViewName = itemView.findViewById(R.id.textView_sessionEntry_name)
            textViewDate = itemView.findViewById(R.id.textView_sessionEntry_date)
            textViewHeartrate = itemView.findViewById(R.id.textView_sessionEntry_bpm)
            textViewTimeSpent = itemView.findViewById(R.id.textView_sessionEntry_timeSpent)
            textViewEmotion = itemView.findViewById(R.id.textView_sessionEntry_emotion)
            layout = itemView.findViewById(R.id.layout_sessionEntry)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_session, parent, false)
        val holder = ViewHolder(view)
        return holder
    }

    override fun getItemCount(): Int {
        return sessionList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = sessionList[position]
        val context = holder.layout.context
        val session = sessionList[position]
        holder.textViewName.text = session.name
        // TODO: format the date nicely to show just the day month and year
        holder.textViewDate.text = session.sessionDate.toString()
        // TODO: format the time to show it in hours and minutes
        holder.textViewTimeSpent.text = session.elapsedTime.toString()
        // TODO: format the money nicely to show it like 79 BPM
        holder.textViewHeartrate.text = session.heartRate.toString()
        // TODO: verify this works in displaying the emoji
        holder.textViewEmotion.text = try {
            Session.EMOTION.valueOf(session.emotion).emoji
        } catch (ex: IllegalArgumentException) {
            "¯\\_(ツ)_/¯"
        }
        holder.layout.isLongClickable = true
        holder.layout.setOnLongClickListener {
            // the textview you want the PopMenu to be anchored to should be added below replacing holder.textViewName
            val popMenu = PopupMenu(context, holder.textViewName)
            popMenu.inflate(R.menu.menu_session_list_context)
            popMenu.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.menu_session_delete -> {
                        deleteFromBackendless(position)
                        true
                    }
                    else -> true
                }
            }
            popMenu.show()
            true
        }

    }
    private fun deleteFromBackendless(position: Int) {
        Log.d("SessionAdapter", "deleteFromBackendless: Trying to delete ${sessionList[position]}")
        // put in the code to delete the item using the callback from Backendless
        // in the handleResponse, we'll need to also delete the item from the sessionList
        //(will need to update the variable to make it mutable)
        // and make sure that the recyclerview is updated using notifyDatasetChanged
        // you can instead use notifyItemRemoved because we know what position was removed. it's more efficient to do that
        Backendless.Data.of<Session?>(Session::class.java)
            .save(sessionList[position], object : AsyncCallback<Session?> {
                override fun handleResponse(savedContact: Session?) {
                    Backendless.Data.of<Session?>(Session::class.java).remove(
                        savedContact,
                        object : AsyncCallback<Long?> {
                            override fun handleResponse(response: Long?) {
                                Log.d("SessionAdapter", "session ${sessionList[position]} deleted")
                                // Contact has been deleted. The response is the
                                // time in milliseconds when the object was deleted
                            }

                            override fun handleFault(fault: BackendlessFault?) {
                                Log.d("SessionAdapter", "fault ${fault?.message}")
                                // an error has occurred, the error code can be
                                // retrieved with fault.getCode()
                            }
                        })
                }

                override fun handleFault(fault: BackendlessFault?) {
                    Log.d("SessionAdapter", "fault ${fault?.message}")
                    // an error has occurred, the error code can be retrieved with fault.getCode()
                }
            })
    }
}