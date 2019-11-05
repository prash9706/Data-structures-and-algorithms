
public class Assignment1 {
	
	/*Method adds N random nodes in the list at the end*/
	private ListNode addNNodes(ListNode head, int count){
		if(count==0){return null;}
		int val = (int) (Math.random()*(10-1)+1);
		ListNode node = new ListNode(val);
		if(head==null){
			head = node;
		}else{
			head.next = node;
		}
		node.next = addNNodes(node, count-1);
		return node;
	}
	
	/*Given the head, prints all the elements in the Linked list*/
	private void printList(ListNode node){
		if(node==null){return;}
		System.out.print(node.val+" ");
		printList(node.next);
	}
	
	/*Method sorts the list in ascending order*/
	private ListNode sortList(ListNode node){
		if(node==null || node.next==null){return node;}
		ListNode temp = node;
		ListNode prev = node;
		while(temp.next!=null){
			if(prev.next.val>temp.next.val){
				prev=temp;
			}
			temp = temp.next;
		}
		if(prev.next.val>node.val){
			node.next = sortList(node.next);
			return node;
		}else{
			ListNode smaller = prev.next;
			prev.next = smaller.next;
			smaller.next = sortList(node);
			return smaller;
		}
	}
	
	public static void main(String[] args){
		Assignment1 list = new Assignment1();
		ListNode head = list.addNNodes(null, 25); //Call to add 25 Node to the list
		System.out.print("Before sorting ==> ");
		list.printList(head); // Traverse the list before sorting
		head=list.sortList(head); //Call to sort the List
		System.out.print("\nAfter sorting ==> ");
		list.printList(head); //Traverse the list after sorting
	}
	
	/*Node Class*/
	public class ListNode {
	   int val;
	   ListNode next;
	   ListNode(int x) { val = x; }
	}
}