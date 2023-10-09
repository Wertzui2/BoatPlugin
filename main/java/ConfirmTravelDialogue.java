import org.bukkit.Bukkit;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;

public class ConfirmTravelDialogue {
    Player player;
    MyListener invoker;
    public ConfirmTravelDialogue(Player p, MyListener invoker){
        player = p;
        this.invoker = invoker;
        ConversationFactory test = new ConversationFactory(invoker.plugin);
        //ConfirmTravelDialogue p = new ConfirmTravelDialogue(e.getPlayer(), this);
        test.withFirstPrompt(getFirstPrompt());
        Conversation testConvo = test.buildConversation(player);
        testConvo.begin();
        //Bukkit.broadcastMessage("This happened");
    }



    public Prompt ConfirmTravel = new ValidatingPrompt() {
        @Override
        public String getPromptText(ConversationContext context) {
            return "Are you sure you want to travel this route? (yes/no)";
        }

        @Override
        public Prompt acceptValidatedInput(ConversationContext context, String input) {
            if(input.equals("yes"))
                invoker.travel(player);

            return Prompt.END_OF_CONVERSATION;
        }
        @Override
        public boolean isInputValid(ConversationContext context, String input){
            return (input.equals("yes") || input.equals("no"));

        }
    };

    public Prompt getFirstPrompt(){return ConfirmTravel;}
}
